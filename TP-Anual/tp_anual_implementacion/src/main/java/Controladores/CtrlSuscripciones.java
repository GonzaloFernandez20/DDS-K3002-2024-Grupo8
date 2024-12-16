package Controladores;

import DTOs.HeladeraSuscripcionDTO;
import DTOs.SuscripcionDTO;
import Repositories.Suscripciones.SuscripcionesRepository;
import Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.suscripcion.GestorDeSuscripciones;
import Modelo.Mappers.HeladeraSuscripcionMapper;
import Modelo.Mappers.SuscripcionMapper;
import Modelo.seguridad.GestorInicioDeSesion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CtrlSuscripciones {

   //DEPENDENCIAS: repositorios y gestores -------------------------------------------------------------------------------------------------------------------------
    private final HeladeraRepository repositorioHeladeras;
    private final SuscripcionesRepository suscripcionesRepository;
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final GestorDeSuscripciones gestorDeSuscripciones;

    @Autowired
    public CtrlSuscripciones(HeladeraRepository repositorioHeladeras,
                             SuscripcionesRepository suscripcionesRepository,
                             GestorInicioDeSesion gestorInicioDeSesion,
                             GestorDeSuscripciones gestorDeSuscripciones) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.suscripcionesRepository = suscripcionesRepository;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.gestorDeSuscripciones = gestorDeSuscripciones;
    }

    //GET MAPPING -----------------------------------------------------------------------------------------------------------------------------------------------
    @GetMapping("/ModificarColaboradorHumanoSuscripciones")
    public String mostrarHeladeras(Model model) {
        List <SuscripcionDTO> suscripcionesDelColaborador = suscripcionesRepository.findByColaborador(gestorInicioDeSesion.obtenerColaboradorPorID()).
                                                                                    stream().
                                                                                    map(suscripcion -> SuscripcionMapper.convertirEnSuscripcionDTO(suscripcion)).
                                                                                    collect(Collectors.toList());
        List<HeladeraSuscripcionDTO> heladeras = repositorioHeladeras.findAll().
                                                                    stream().
                                                                    map(heladera -> HeladeraSuscripcionMapper.convertirEnHeladeraSuscripcionDTO(heladera, suscripcionesDelColaborador)).
                                                                    collect(Collectors.toList());
        model.addAttribute("suscripciones", suscripcionesDelColaborador);
        model.addAttribute("heladeras", heladeras);
        return "ModificarColaboradorHumanoSuscripciones";
    }

    //POST MAPPING -----------------------------------------------------------------------------------------------------------------------------------------------
    @PostMapping("/ModificarColaboradorHumanoSuscripciones")
    public String suscribirColaborador(@RequestParam(value = "optionsHeladera") int idHeladeraElegida,
                                       @RequestParam(required = false, value = "check-suscripcion-disponen-viandas") boolean checkSuscripcionDisponenViandas,
                                       @RequestParam(value = "cantidadDeViandasDisponibles", defaultValue = "0") int cantidadDeViandasDisponibles,
                                       @RequestParam(required = false, value = "check-suscripcion-faltan-viandas") boolean checkSuscripcionFaltanViandas,
                                       @RequestParam(value = "cantidadDeViandasFaltantes",defaultValue = "0") int cantidadDeViandasFaltantes,
                                       @RequestParam(required = false, value = "check-suscripcion-desperfecto") boolean checkSuscripcionDesperfecto,
                                       Model model) {

        Heladera heladeraElegida = repositorioHeladeras.findById(idHeladeraElegida).get();
        Colaborador suscriptor = gestorInicioDeSesion.obtenerColaboradorPorID();

        suscribir(checkSuscripcionDisponenViandas, heladeraElegida, suscriptor, "Quedan " + cantidadDeViandasDisponibles + " viandas");
        suscribir(checkSuscripcionFaltanViandas, heladeraElegida,suscriptor,  "Faltan " + cantidadDeViandasFaltantes + " viandas");
        suscribir(checkSuscripcionDesperfecto, heladeraElegida, suscriptor, "Se produjo una falla");
        gestorDeSuscripciones.guardarSuscripciones(heladeraElegida);

        model.addAttribute("mensaje", "Suscripciones registradas exitosamente.");
        return mostrarHeladeras(model);
    }

    private void suscribir(boolean checkbox, Heladera heladera, Colaborador colaborador, String evento) {
        if(checkbox) {
            gestorDeSuscripciones.registrarSuscripcion(heladera, colaborador, evento);
        }
    }

    @PostMapping("/EliminarSuscripcion")
    public ResponseEntity<String> desuscribir( @RequestParam String id_heladera, @RequestParam String evento){

        Optional<Heladera> heladera = repositorioHeladeras.findById(Integer.parseInt(id_heladera));
        Colaborador colaborador  = gestorInicioDeSesion.obtenerColaboradorPorID();

        gestorDeSuscripciones.efectuarDesuscripcion(heladera.get(), colaborador, evento);
        return ResponseEntity.ok().body("Desuscribir suscripcion exitosamente.");
    }
}
