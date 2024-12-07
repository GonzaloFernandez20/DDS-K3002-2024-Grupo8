package Controladores;

import DTOs.HeladeraDTO;
import DTOs.AlertaDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.Repositories.incidentes.AlertaRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.Alerta;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Modelo.Mappers.AlertaMapper;
import Modelo.Mappers.HeladeraMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import Repositorios.RepositorioHeladeras;
import Repositorios.RepositorioIncidentes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class CtrlGestionHeladeras {

    private List<HeladeraDTO> heladeras;
    private List<AlertaDTO> alertas;

    private final HeladeraRepository heladeraRepository;
    private final AlertaRepository alertaRepository;

    @Autowired
    public CtrlGestionHeladeras(HeladeraRepository repositorioHeladeras, AlertaRepository alertaRepository) {
        this.heladeraRepository = repositorioHeladeras;
        this.alertaRepository = alertaRepository;
    }

    @GetMapping("/ModificarColaboradorJuridicoHeladeras")
    public String mostrarHeladerasYAlertas(Model model) {
        heladeras = heladeraRepository.findAll().stream().
                map(heladera -> HeladeraMapper.convertirEnHeladeraDTO(heladera)).collect(Collectors.toList());
        alertas = alertaRepository.findAll().stream().
                map(alerta -> AlertaMapper.convertirEnAlertaDTO(alerta)).collect(Collectors.toList());

        model.addAttribute("warnings", alertas);
        model.addAttribute("heladeras", heladeras);

        return "ModificarColaboradorJuridicoHeladeras";
    }

    @PostMapping("/ModificarHeladera")
    public String modificarHeladera(@RequestParam("mod-heladera-punto-title") int idHeladera,
                                    @RequestParam(name = "nuevaCiudadHeladera", required = false) String nombreCiudad,
                                    @RequestParam(name = "nuevaCalleHeladera", required = false) String calle,
                                    @RequestParam(name = "nuevaAlturaHeladera", required = false) String altura,
                                    @RequestParam(name = "nuevoModeloHeladera", required = false) String modelo,
                                    @RequestParam(name = "nuevaTempMax", required = false) Integer tempMax,
                                    @RequestParam(name = "nuevaTempMin", required = false) Integer tempMin,
                                    @RequestParam(name = "nuevaCapacidadHeladera", required = false) Integer capacidad,
                                    Model model) {
        Heladera heladeraAModificar = heladeraRepository.findById(idHeladera).get();

        if(Objects.isNull(heladeraAModificar)) {
            model.addAttribute("mensaje", "Error: No eligió una heladera válida");
            return mostrarHeladerasYAlertas(model);
        }

        if(!Objects.isNull(nombreCiudad)) {
            heladeraAModificar.getUbicacion().setCiudad(nombreCiudad);
        }

        if(!Objects.isNull(calle)) {
            heladeraAModificar.getUbicacion().getDireccion().setCalle(calle);
        }

        if(!Objects.isNull(altura)) {
            heladeraAModificar.getUbicacion().getDireccion().setAltura(altura);
        }

        if(!Objects.isNull(modelo)) {
            heladeraAModificar.getModelo().setNombreModelo(modelo);
        }

        if(!Objects.isNull(tempMax)) {
            heladeraAModificar.getModelo().setTemperaturaMaxima(tempMax);
        }

        if(!Objects.isNull(tempMin)) {
            heladeraAModificar.getModelo().setTemperaturaMinima(tempMin);
        }

        if(!Objects.isNull(capacidad)) {
            heladeraAModificar.setCapacidadDeViandas(capacidad);
        }

        heladeraRepository.save(heladeraAModificar);

        model.addAttribute("mensaje", "¡Felicitaciones! Se pudo modificar la información de la heladera exitosamente.");

        System.out.println(heladeraAModificar.getIdHeladera());
        System.out.println(heladeraAModificar.getUbicacion().getCiudad());
        System.out.println(heladeraAModificar.getUbicacion().getDireccion().getCalle());
        System.out.println(heladeraAModificar.getUbicacion().getDireccion().getAltura());
        System.out.println(heladeraAModificar.getModelo().getNombreModelo());
        System.out.println(heladeraAModificar.getModelo().getTemperaturaMaxima());
        System.out.println(heladeraAModificar.getModelo().getTemperaturaMinima());
        System.out.println(heladeraAModificar.getCapacidadDeViandas());

        return mostrarHeladerasYAlertas(model);
    }

    @PostMapping("/EliminarHeladera")
    public String eliminarHeladera(@RequestParam("heladera-por-eliminar") int idHeladera,
                                    Model model) {
        Heladera heladeraAEliminar = heladeraRepository.findById(idHeladera).get();
        heladeraRepository.delete(heladeraAEliminar);

        System.out.println("Se elimina la heladera " + idHeladera);

        model.addAttribute("mensaje", "La heladera se ha dado de baja con éxito.");

        return mostrarHeladerasYAlertas(model);
    }
}
