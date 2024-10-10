package tp_anual.tp_anual_implementacion.Controllers;

import DTOs.HeladeraDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.suscripcion.GestorDeSuscripciones;
import Repositorios.RepositorioHeladeras;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CtrlSuscripciones {

    private final List<HeladeraDTO> heladeras = RepositorioHeladeras.getInstancia().getHeladeras().stream().map(heladera -> convertirHeladeraADTO(heladera)).collect(Collectors.toList());
    // private final Stream<SuscripcionDTO> suscripciones = RepositorioSuscripciones.getInstancia().getSuscripciones.stream().map(suscripcion -> convertirSuscripcionADTO(suscripcion));
    // PENDIENTE

    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200", "1234")), List.of(new WhatsApp("15 2350-2350")));
    //

    @GetMapping("/ModificarColaboradorHumanoSuscripciones")
    public String mostrarHeladeras(Model model) {
        // model.addAttribute("suscripciones", suscripciones);
        System.out.println("Muestra las heladeras");
        model.addAttribute("heladeras", heladeras);
        return "ModificarColaboradorHumanoSuscripciones";
    }

    @PostMapping("/ModificarColaboradorHumanoSuscripciones")
    public String recibirSeleccion(@RequestParam(value = "optionsHeladera", defaultValue = "0") String idHeladeraElegida,
                                   @RequestParam(required = false, value = "check-suscripcion-disponen-viandas") boolean checkSuscripcionDisponenViandas, @RequestParam(value = "cantidadDeViandasDisponibles", defaultValue = "0") int cantidadDeViandasDisponibles,
                                   @RequestParam(required = false, value = "check-suscripcion-faltan-viandas") boolean checkSuscripcionFaltanViandas, @RequestParam(value = "cantidadDeViandasFaltantes",defaultValue = "0") int cantidadDeViandasFaltantes,
                                   @RequestParam(value = "check-suscripcion-desperfecto") boolean checkSuscripcionDesperfecto) {

        Heladera heladeraElegida = RepositorioHeladeras.getInstancia().buscarHeladeraPorId(Integer.parseInt(idHeladeraElegida));

        // TO DO: CHECKEAR QUE CUANDO EL CHECKBOX ESTÉ EN TRUE HAYA ALGO EN EL INPUT

        String suscripcionesValidas = "";

        if(validarSuscripcion(checkSuscripcionDisponenViandas, heladeraElegida, "quedan " + cantidadDeViandasDisponibles + " viandas")) {
            suscripcionesValidas += "Notificar cuando disponga únicamente de " + cantidadDeViandasDisponibles + " viandas. ";
        }

        if(validarSuscripcion(checkSuscripcionFaltanViandas, heladeraElegida, "faltan " + cantidadDeViandasFaltantes + " viandas")) {
            suscripcionesValidas += "Notificar cuando falten " + cantidadDeViandasFaltantes + " viandas para que se llene la heladera. ";
        }

        if(validarSuscripcion(checkSuscripcionDesperfecto, heladeraElegida, "se produjo una falla")) {
            suscripcionesValidas += "Notificar cuando se produjo una falla.";
        }

        if(suscripcionesValidas.isEmpty()) {
            System.out.println("Sus suscripciones fallaron.");
        } else {
            System.out.println("Suscripciones válidas: " + suscripcionesValidas);
        }

        return "ModificarColaboradorHumanoSuscripciones";
    }

    private boolean validarSuscripcion(boolean checkbox, Heladera heladera, String evento) {
        if(checkbox) {
            GestorDeSuscripciones.getInstancia().registrarSuscripcion(heladera, colaborador, evento);
            System.out.println("Voy a guardar "+heladera.getUbicacion().getNombreDelPunto() + ": " + evento);
            /*
            En realidad todo depende de si el gestor logra registrar la suscripción, debería
                            devolver un boolean para mostrar el OK que está en el DS.
            */
            return true;
        }
        System.out.println("No voy a guardar " + heladera.getUbicacion().getNombreDelPunto() + ": " + evento);
        return false;
    }

    private HeladeraDTO convertirHeladeraADTO(Heladera heladera) {
        return new HeladeraDTO(heladera.getColaboradorACargo(), heladera.getCapacidadDeViandas(), heladera.getModelo().getNombreModelo(), heladera.getModelo().getTemperaturaMaxima(), heladera.getModelo().getTemperaturaMinima(), heladera.getUbicacion().getDireccion().getCalle(), heladera.getUbicacion().getDireccion().getAltura(), heladera.getUbicacion().getDireccion().getCodPostal(), heladera.getUbicacion().getCiudad(), heladera.getUbicacion().getNombreDelPunto(), heladera.getPuestaEnFuncionamiento());
    }

    private Heladera procesarDTOHeladera(HeladeraDTO heladeraDTO) {
        Direccion direccionHeladera = new Direccion(heladeraDTO.getCalle(), heladeraDTO.getAltura(), heladeraDTO.getCodPostal());
        Ubicacion ubicacionHeladera = new Ubicacion(direccionHeladera, heladeraDTO.getCiudad(), heladeraDTO.getNombreDelPunto());
        Modelo modeloHeladera = new Modelo(heladeraDTO.getNombreModelo(), heladeraDTO.getTempMAXmodelo(), heladeraDTO.getTempMINmodelo());
        return new Heladera(heladeraDTO.getColaboradorACargo(), ubicacionHeladera, heladeraDTO.getCapacidadViandas(), modeloHeladera, heladeraDTO.getPuestaEnFuncionamiento());
    }
}
