package Controladores.Contribuciones;

import DTOs.HeladeraDTO;
import Repositories.contribucion.HacerseCargoDeHeladeraRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Mappers.BuilderHeladera;
import Modelo.seguridad.GestorInicioDeSesion;
import Servicios_Externos_APIs.API.APIRequester;
import Servicios_Externos_APIs.API.ResponseRecomendacion;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
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
    private final HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository;

    @Autowired
    public CtrlHacerseCargoDeHeladera(GestorInicioDeSesion gestorInicioDeSesion, HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.hacerseCargoDeHeladeraRepository = hacerseCargoDeHeladeraRepository;
    }

    @GetMapping("/HacerseCargoDeUnaHeladera")
    public String HacerseCargoDeUnaHeladera() {
        return "HacerseCargoDeUnaHeladera";
    }

    @GetMapping("/RecomendacionColocacion")
    public String RecomedacionColocacion() {
        return "RecomendacionColocacion";
    }


    @Transactional
    @PostMapping("/FormularioDeHeladera")
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

        Hibernate.initialize(colaborador.getHistorialDeContribuciones());
        nuevaContribucion.procesarLaContribucion();

        hacerseCargoDeHeladeraRepository.save(nuevaContribucion);
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
