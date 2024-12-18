package Controladores.Contribuciones;

import DTOs.HeladeraDTO;

import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Mappers.BuilderHeladera;
import Modelo.seguridad.GestorInicioDeSesion;

import Repositories.colaborador.ColaboradorRepository;
import Repositories.contribucion.HacerseCargoDeHeladeraRepository;
import Repositories.heladera.HeladeraRepository;
import Repositories.Suscripciones.NotificadorDeSuscriptosRepository;

import Repositories.heladera.ModeloRepository;
import Servicios_Externos_APIs.API.APIRequester;
import Servicios_Externos_APIs.API.ResponseRecomendacion;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final NotificadorDeSuscriptosRepository notificadorDeSuscriptosRepository;
    private final ModeloRepository modeloRepository;

    @Autowired
    public CtrlHacerseCargoDeHeladera(GestorInicioDeSesion gestorInicioDeSesion,
                                      HeladeraRepository heladeraRepository,
                                      HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository,
                                      ColaboradorRepository colaboradorRepository,
                                      NotificadorDeSuscriptosRepository notificadorDeSuscriptosRepository,
                                      ModeloRepository modeloRepository) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.heladeraRepository = heladeraRepository;
        this.hacerseCargoDeHeladeraRepository = hacerseCargoDeHeladeraRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.notificadorDeSuscriptosRepository = notificadorDeSuscriptosRepository;
        this.modeloRepository = modeloRepository;
    }

    @GetMapping("/HacerseCargoDeUnaHeladera")
    public String HacerseCargoDeUnaHeladera(Model model) {
        Colaborador colaboradorActual = gestorInicioDeSesion.obtenerColaboradorPorID();
        if (colaboradorActual.getPersona() instanceof PersonaHumana) {
            return "PedirRegistroJuridico";
        }

        return "HacerseCargoDeUnaHeladera";
    }

    @GetMapping("/RecomendacionColocacion")
    public String RecomendacionColocacion() {
        return "RecomendacionColocacion";
    }


    @PostMapping("/FormularioDeHeladera")
    @Transactional
    public ResponseEntity<String> formularioDeHeladera(@RequestBody HeladeraDTO heladeraDTO) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        heladeraDTO.setColaboradorACargo(colaborador);
        Modelo modelo = modeloRepository.obtenerModeloSegunNombre(heladeraDTO.getNombreModelo());
        Heladera nuevaHeladera = BuilderHeladera.crearHeladeraAPartirDe(heladeraDTO, modelo);
        NotificadorDeSuscriptos notificador = new NotificadorDeSuscriptos(nuevaHeladera);
        nuevaHeladera.setNotificadorDeSuscriptos(notificador);
        NotificadorDeSuscriptos notificadorGuardado = notificadorDeSuscriptosRepository.save(notificador);


        System.out.println("Nueva Heladera: " + nuevaHeladera.getUbicacion().getNombreCompletoDeUbicacion());

        // Esto creo no lo deberia hacer el controlador pero de momento queda aca
        // Contribucion nuevaContribucion
        HacerseCargoDeHeladera nuevaContribucion = new HacerseCargoDeHeladera();
        nuevaContribucion.setColaborador(colaborador);
        nuevaContribucion.setHeladeraACargo(notificadorGuardado.getHeladera());
        nuevaContribucion.setFechaDeContribucion(LocalDate.now());



        nuevaContribucion.procesarLaContribucion();

        try {
            hacerseCargoDeHeladeraRepository.save(nuevaContribucion);
        } catch (Exception e) {
            e.printStackTrace(); //
        }
        // Usando cascade = CascadeType.PERSIST estoy guardando la heladera también

        return ResponseEntity.ok("Registro realizado con éxito!");
    }

    @GetMapping("/ObtenerPuntosRecomendados")
    public ResponseEntity<List<PuntoEnElMapa>> recomendarPuntos(@RequestParam double latitud,
                                                 @RequestParam double longitud,
                                                 @RequestParam int radio) throws IOException {
        ResponseRecomendacion puntos = APIRequester.getInstancia().obtenerPuntosRecomendados(latitud, longitud, radio);
        List<PuntoEnElMapa> puntosRecomendados = puntos.getPosiblesPuntosDeColocacion();
        return ResponseEntity.status(HttpStatus.OK).body(puntosRecomendados);
    }
}
