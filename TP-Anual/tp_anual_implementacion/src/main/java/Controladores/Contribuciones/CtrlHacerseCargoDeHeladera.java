package Controladores.Contribuciones;

import DTOs.HeladeraDTO;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.Repositories.contribucion.HacerseCargoDeHeladeraRepository;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Mappers.BuilderHeladera;
import Modelo.seguridad.GestorInicioDeSesion;
import Servicios_Externos_APIs.API.APIRequester;
import Servicios_Externos_APIs.API.ResponseRecomendacion;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CtrlHacerseCargoDeHeladera {

    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final HeladeraRepository heladeraRepository;
    private final HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository;
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    public CtrlHacerseCargoDeHeladera(GestorInicioDeSesion gestorInicioDeSesion, HeladeraRepository heladeraRepository, HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository, ColaboradorRepository colaboradorRepository) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.heladeraRepository = heladeraRepository;
        this.hacerseCargoDeHeladeraRepository = hacerseCargoDeHeladeraRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    @GetMapping("/HacerseCargoDeUnaHeladera")
    public String HacerseCargoDeUnaHeladera() {
        return "HacerseCargoDeUnaHeladera";
    }

    @GetMapping("/RecomendacionColocacion")
    public String RecomedacionColocacion() {
        return "RecomendacionColocacion";
    }


    @PostMapping("/FormularioDeHeladera")
    @Transactional
    public ResponseEntity<String> formularioDeHeladera(@RequestBody HeladeraDTO heladeraDTO) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        heladeraDTO.setColaboradorACargo(colaborador);
        Heladera nuevaHeladera = BuilderHeladera.crearHeladeraAPartirDe(heladeraDTO);

        System.out.println("Nueva Heladera: " + nuevaHeladera.getUbicacion().getNombreCompletoDeUbicacion());

        //Esto creo no lo deberia hacer el controlador pero de momento queda aca
        // Contribucion nuevaContribucion
        HacerseCargoDeHeladera nuevaContribucion = new HacerseCargoDeHeladera();
        nuevaContribucion.setColaborador(colaborador);
        nuevaContribucion.setHeladeraACargo(nuevaHeladera);
        nuevaContribucion.setFechaDeContribucion(LocalDate.now());

        System.out.println(colaborador.getId_colaborador() + " " + nuevaHeladera.getIdHeladera());

        nuevaContribucion.procesarLaContribucion();

        try {
            hacerseCargoDeHeladeraRepository.save(nuevaContribucion);
        } catch (Exception e) {
            e.printStackTrace(); //
        }
        // Usando cascade = CascadeType.PERSIST estoy guardando la heladera también

        return ResponseEntity.ok("Registro realizado con éxito!");
    }

    @PostMapping("/ObtenerPuntosRecomendados")
    public ResponseEntity<List<PuntoEnElMapa>> recomendarPuntos(@RequestParam double latitud,
                                                 @RequestParam double longitud,
                                                 @RequestParam int radio) throws IOException {
        ResponseRecomendacion puntos = APIRequester.getInstancia().obtenerPuntosRecomendados(latitud, longitud, radio);
        List<PuntoEnElMapa> puntosRecomendados = puntos.getPosiblesPuntosDeColocacion();
        return ResponseEntity.status(HttpStatus.OK).body(puntosRecomendados);
    }
}
