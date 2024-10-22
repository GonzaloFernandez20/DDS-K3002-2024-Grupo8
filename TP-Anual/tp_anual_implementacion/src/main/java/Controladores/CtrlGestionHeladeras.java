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

import java.util.List;
import java.util.stream.Collectors;

/*  TO DO:
* - Poder modificar una heladera (/ModificarHeladera)
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
    public String modificarHeladera(Model model) {
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
