package Controladores;

import DTOs.HeladeraDTO;
import DTOs.SuscripcionDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Modelo.Dominio.suscripcion.GestorDeSuscripciones;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Repositorios.RepositorioHeladeras;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CtrlSuscripciones {

    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200")), List.of(new WhatsApp("15 2350-2350")));
    //

    private final List<HeladeraDTO> heladeras = RepositorioHeladeras.getInstancia().getHeladeras().stream().map(heladera -> convertirHeladeraADTO(heladera)).collect(Collectors.toList());
    List<SuscripcionDTO> suscripciones = conseguirSuscripcionesDTO();

    @GetMapping("/ModificarColaboradorHumanoSuscripciones")
    public String mostrarHeladeras(Model model) {
        model.addAttribute("suscripciones", suscripciones);
        System.out.println("Muestra las heladeras");
        model.addAttribute("heladeras", heladeras);
        return "ModificarColaboradorHumanoSuscripciones";
    }

    @PostMapping("/ModificarColaboradorHumanoSuscripciones")
    public String recibirSeleccion(@RequestParam(value = "optionsHeladera", defaultValue = "0") String idHeladeraElegida,
                                   @RequestParam(required = false, value = "check-suscripcion-disponen-viandas") boolean checkSuscripcionDisponenViandas,
                                   @RequestParam(value = "cantidadDeViandasDisponibles", defaultValue = "0") int cantidadDeViandasDisponibles,
                                   @RequestParam(required = false, value = "check-suscripcion-faltan-viandas") boolean checkSuscripcionFaltanViandas,
                                   @RequestParam(value = "cantidadDeViandasFaltantes",defaultValue = "0") int cantidadDeViandasFaltantes,
                                   @RequestParam(required = false, value = "check-suscripcion-desperfecto") boolean checkSuscripcionDesperfecto,
                                   Model model) {

        Heladera heladeraElegida = RepositorioHeladeras.getInstancia().buscarHeladeraPorId(Integer.parseInt(idHeladeraElegida));

        String suscripcionesValidas = "";

        if(realizarSuscripcion(checkSuscripcionDisponenViandas, heladeraElegida, "Quedan " + cantidadDeViandasDisponibles + " viandas.")) {
            suscripcionesValidas += "Notificar cuando disponga únicamente de " + cantidadDeViandasDisponibles + " viandas. ";
        }

        if(realizarSuscripcion(checkSuscripcionFaltanViandas, heladeraElegida, "Faltan " + cantidadDeViandasFaltantes + " viandas.")) {
            suscripcionesValidas += "Notificar cuando falten " + cantidadDeViandasFaltantes + " viandas para que se llene la heladera. ";
        }

        if(realizarSuscripcion(checkSuscripcionDesperfecto, heladeraElegida, "Se produjo una falla.")) {
            suscripcionesValidas += "Notificar cuando se produjo una falla.";
        }

        if(suscripcionesValidas.isEmpty()) {
            model.addAttribute("mensaje", "Sus suscripciones fallaron.");
        } else {
            System.out.println("Suscripciones válidas: " + suscripcionesValidas);
            model.addAttribute("mensaje", "Suscripciones válidas: " + suscripcionesValidas);
        }
        System.out.println(suscripcionesValidas);

        return mostrarHeladeras(model);
    }

    private boolean realizarSuscripcion(boolean checkbox, Heladera heladera, String evento) {
        if(checkbox) {
            GestorDeSuscripciones.getInstancia().registrarSuscripcion(heladera, colaborador, evento);
            System.out.println("Voy a guardar "+heladera.getUbicacion().getNombreDelPunto() + ": " + evento);
        }

        Map<String, List<Colaborador>> suscriptos = heladera.getNotificadorDeSuscriptos().getSuscriptos();

        try {
            boolean colaboradorQuedoSuscripto = suscriptos.get(evento).contains(colaborador);
            if(colaboradorQuedoSuscripto) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("No voy a guardar " + heladera.getUbicacion().getNombreDelPunto() + ": " + evento);
        return false;
    }

    private HeladeraDTO convertirHeladeraADTO(Heladera heladera) {
        return new HeladeraDTO(heladera.getid_heladera(), heladera.getColaboradorACargo(), heladera.getCapacidadDeViandas(), heladera.getModelo().getNombreModelo(), heladera.getModelo().getTemperaturaMaxima(), heladera.getModelo().getTemperaturaMinima(), heladera.getUbicacion().getDireccion().getCalle(), heladera.getUbicacion().getDireccion().getAltura(), heladera.getUbicacion().getCiudad(), heladera.getUbicacion().getNombreDelPunto(), heladera.getPuestaEnFuncionamiento());
    } // Investigar el uso de model mapper para mapeo de objetos en dtos

    private Heladera procesarDTOHeladera(HeladeraDTO heladeraDTO) {
        Direccion direccionHeladera = new Direccion(heladeraDTO.getCalle(), heladeraDTO.getAltura());
        Ubicacion ubicacionHeladera = new Ubicacion(direccionHeladera, heladeraDTO.getCiudad(), heladeraDTO.getNombreDelPunto());
        Modelo modeloHeladera = new Modelo(heladeraDTO.getTempMAXmodelo(), heladeraDTO.getTempMINmodelo());
        return new Heladera(heladeraDTO.getColaboradorACargo(), ubicacionHeladera, heladeraDTO.getCapacidadViandas(), modeloHeladera, heladeraDTO.getPuestaEnFuncionamiento());
    }

    private List<SuscripcionDTO> conseguirSuscripcionesDTO() {
        List<SuscripcionDTO> suscripciones = new ArrayList<>();

        // LINEA PARA CUANDO TENGAMOS EL REPO
        // List<NotificadorDeSuscriptos> notificadores = RepositorioSuscripciones.getInstancia().getSuscripciones();
        List<NotificadorDeSuscriptos> notificadores = List.of(
                new NotificadorDeSuscriptos(
                        new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50"), "CABA", "Gastronomos Argentinos 1"), 15, new Modelo(15, -2), LocalDate.now())),
                new NotificadorDeSuscriptos(
                        new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50"), "CABA", "Gastronomos Argentinos 2"), 15, new Modelo(15, -2), LocalDate.now())
                )
        );
        notificadores.get(0).suscribir("Se produjo una falla.", colaborador);
        notificadores.get(0).suscribir("Faltan 5 viandas.", colaborador);
        notificadores.get(1).suscribir("Quedan 2 viandas.", colaborador);

        for(int i=0; i<notificadores.size(); i++) {
            NotificadorDeSuscriptos unNotificador = notificadores.get(i);

            List<String> eventosSuscriptos = unNotificador.eventosALosQueEstaSuscritoUnColaborador(colaborador);

            if(!eventosSuscriptos.isEmpty()) {
                eventosSuscriptos.forEach( unEvento ->
                    suscripciones.add(new SuscripcionDTO(colaborador.getId_colaborador(), unNotificador.getHeladera().getUbicacion().getNombreCompletoDeUbicacion(), unEvento))
                );
            }
        }

        return suscripciones;
    }
}
