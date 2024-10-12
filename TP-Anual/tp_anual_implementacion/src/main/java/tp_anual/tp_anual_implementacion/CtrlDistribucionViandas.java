package tp_anual.tp_anual_implementacion;

import DTOs.DistribucionDeViandaDTO;
import DTOs.HeladeraDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.MotivoDeDistribucion;
import Modelo.Dominio.contribucion.DistribucionDeVianda;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Repositorios.RepositorioHeladeras;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CtrlDistribucionViandas {
    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200", "1234")), List.of(new WhatsApp("15 2350-2350")));
    //
    private final List<HeladeraDTO> heladeras = RepositorioHeladeras.getInstancia().getHeladeras().stream().map(heladera -> convertirHeladeraADTO(heladera)).collect(Collectors.toList());;
    //model.addAtribute("heladeras", new DistribucionDeVianda(colaborador, heladeraOrigen, heladeraDestino, MotivoDeDistribucion.FALTA_DE_VIANDAS, 2, LocalDate.now()));

    List<MotivoDeDistribucion> motivos = new ArrayList<>();

    public void setMotivos() {
        motivos.add(MotivoDeDistribucion.FALTA_DE_VIANDAS);
        motivos.add(MotivoDeDistribucion.DESPERFECTO_HELADERA);
    }

    @GetMapping("/DistribuirVianda")
    public String mostrarFormulario(Model model) {
        model.addAttribute("heladeras", heladeras);
        this.setMotivos();
        model.addAttribute("motivos", motivos);

        return "DistribuirVianda";
    }

    @PostMapping("/DistribuirViandaRespuesta")
    public String procesarFormulario(@ModelAttribute DistribucionDeViandaDTO distribucionDeViandaDTO) {
        System.out.println("cantidad de viandas a donar" + distribucionDeViandaDTO.getCantidadDeViandas());
        DistribucionDeVianda distribucionDeVianda1 = procesarDTO(distribucionDeViandaDTO);
        // agregar a la base de datos

        // Mensaje de exito
        //model.addAttribute("mensaje", "Distribución realizada con éxito!");

        return "Home";
    }

    public DistribucionDeVianda procesarDTO(DistribucionDeViandaDTO distribucionDeViandaDTO) {
        return new DistribucionDeVianda(distribucionDeViandaDTO.getColaborador(), distribucionDeViandaDTO.getHeladeraDeOrigen(), distribucionDeViandaDTO.getHeladeraDestino(), distribucionDeViandaDTO.getMotivoDeDistribucion(), distribucionDeViandaDTO.getCantidadDeViandas(), LocalDate.now());
    }

    private HeladeraDTO convertirHeladeraADTO(Heladera heladera) {
        return new HeladeraDTO(heladera.getColaboradorACargo(), heladera.getCapacidadDeViandas(), heladera.getModelo().getNombreModelo(), heladera.getModelo().getTemperaturaMaxima(), heladera.getModelo().getTemperaturaMinima(), heladera.getUbicacion().getDireccion().getCalle(), heladera.getUbicacion().getDireccion().getAltura(), heladera.getUbicacion().getDireccion().getCodPostal(), heladera.getUbicacion().getCiudad(), heladera.getUbicacion().getNombreDelPunto(), heladera.getPuestaEnFuncionamiento());
    }
}
