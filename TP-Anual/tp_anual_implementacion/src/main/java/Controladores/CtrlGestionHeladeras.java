package Controladores;

import DTOs.HeladeraDTO;
import DTOs.AlertaDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.Alerta;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import Repositorios.RepositorioHeladeras;
import Repositorios.RepositorioIncidentes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*  TO DO:
* - Poder dar de baja una heladera (/EliminarHeladera)
* - Tener una forma consistente de que las pantallas que compartan el jurídico y el humano
* se puedan mostrar igual pero cambiando determinadas partes de la pantalla--> EN CAMINO... YA ESTÁS POR LLEGAR!
* */

@Controller
public class CtrlGestionHeladeras {
    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com")));
    //
    private final List<HeladeraDTO> heladeras = RepositorioHeladeras.getInstancia().getHeladeras().stream().map(heladera -> convertirHeladeraADTO(heladera)).collect(Collectors.toList());
    private final List<AlertaDTO> alertas = RepositorioIncidentes.getInstancia().getAlertas().stream().map(alerta -> convertirAlertaADTO(alerta)).collect(Collectors.toList());

    @GetMapping("/ModificarColaboradorJuridicoHeladeras")
    public String mostrarHeladerasYAlertas(Model model) {
        model.addAttribute("warnings", alertas);
        model.addAttribute("heladeras", heladeras);

        return "ModificarColaboradorJuridicoHeladeras";
    }

    @PostMapping("/ModificarHeladera")
    public String modificarHeladera(@RequestParam("mod-heladera-punto-title") int idHeladera,
                                    @RequestParam(name = "nuevaCiudadHeladera", required = false) String nombreCiudad,
                                    @RequestParam(name = "nuevaCalleHeladera", required = false) String calle,
                                    @RequestParam(name = "nuevaAlturaHeladera", required = false) String altura,
                                    @RequestParam(name = "nuevoCodigoPostal", required = false) String codigoPostal,
                                    @RequestParam(name = "nuevoModeloHeladera", required = false) String modelo,
                                    @RequestParam(name = "nuevaTempMax", required = false) Integer tempMax,
                                    @RequestParam(name = "nuevaTempMin", required = false) Integer tempMin,
                                    @RequestParam(name = "nuevaCapacidadHeladera", required = false) Integer capacidad,
                                    Model model) {
        HeladeraDTO heladeraAModificar = heladeras.stream().filter(heladera -> heladera.getIdHeladera() == idHeladera).findFirst().orElse(null);

        if(Objects.isNull(heladeraAModificar)) {
            model.addAttribute("mensaje", "Error: No eligió una heladera válida");
            return mostrarHeladerasYAlertas(model);
        }

        if(!Objects.isNull(nombreCiudad)) {
            heladeraAModificar.setCiudad(nombreCiudad);
        }

        if(!Objects.isNull(calle)) {
            heladeraAModificar.setCalle(calle);
        }

        if(!Objects.isNull(altura)) {
            heladeraAModificar.setAltura(altura);
        }

        if(!Objects.isNull(codigoPostal)) {
            heladeraAModificar.setCodPostal(codigoPostal);
        }

        if(!Objects.isNull(modelo)) {
            heladeraAModificar.setNombreModelo(modelo);
        }

        if(!Objects.isNull(tempMax)) {
            heladeraAModificar.setTempMAXmodelo(tempMax);
        }

        if(!Objects.isNull(tempMin)) {
            heladeraAModificar.setTempMINmodelo(tempMin);
        }

        if(!Objects.isNull(capacidad)) {
            heladeraAModificar.setCapacidadViandas(capacidad);
        }

        // RepositorioHeladeras.modificarHeladera(heladeraAModificar);
        // LO DEBE RECIBIR EL REPOSITORIO PARA ACTUALIZARLO EN LA BD

        model.addAttribute("mensaje", "¡Felicitaciones! Se pudo modificar la información de la heladera exitosamente.");

        System.out.println(heladeraAModificar.getIdHeladera());
        System.out.println(heladeraAModificar.getCiudad());
        System.out.println(heladeraAModificar.getCalle());
        System.out.println(heladeraAModificar.getAltura());
        System.out.println(heladeraAModificar.getCodPostal());
        System.out.println(heladeraAModificar.getNombreModelo());
        System.out.println(heladeraAModificar.getTempMAXmodelo());
        System.out.println(heladeraAModificar.getTempMINmodelo());
        System.out.println(heladeraAModificar.getCapacidadViandas());

        return mostrarHeladerasYAlertas(model);
    }

    @PostMapping("/EliminarHeladera")
    public String eliminarHeladera(Model model) {
        return mostrarHeladerasYAlertas(model);
    }

    int i = 0;

    private HeladeraDTO convertirHeladeraADTO(Heladera heladera) {
        HeladeraDTO heladeraDTO = new HeladeraDTO(heladera.getColaboradorACargo(), heladera.getCapacidadDeViandas(), heladera.getModelo().getNombreModelo(), heladera.getModelo().getTemperaturaMaxima(), heladera.getModelo().getTemperaturaMinima(), heladera.getUbicacion().getDireccion().getCalle(), heladera.getUbicacion().getDireccion().getAltura(), heladera.getUbicacion().getDireccion().getCodPostal(), heladera.getUbicacion().getCiudad(), heladera.getUbicacion().getNombreDelPunto(), heladera.getPuestaEnFuncionamiento());
        heladeraDTO.setIdHeladera(i);
        i++;
        return heladeraDTO;
    }

    private AlertaDTO convertirAlertaADTO(Alerta alerta) {
        List<Integer> idVisitas = alerta.getVisitas().stream().map(visitaTecnica -> visitaTecnica.getIdVisitaTecnica()).collect(Collectors.toList());
        return new AlertaDTO(alerta.getMomentoDelSuceso(), alerta.getHeladeraDondeOcurrio().getIdHeladera(), idVisitas, alerta.getEstado(), alerta.getTipoAlerta());
    }
}
