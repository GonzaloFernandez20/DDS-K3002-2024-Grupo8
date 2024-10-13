package Controladores;

import DTOs.DonacionDeDineroDTO;
import DTOs.DonacionDeViandaDTO;
import DTOs.HeladeraDTO;
import DTOs.ViandaDTO;
import Modelo.Dominio.GestionDeContribuciones.GestorDonacionDeDinero;
import Modelo.Dominio.GestionDeContribuciones.GestorDonacionDeViandas;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.*;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Factorys.BuilderDonacionDeViandas;
import Modelo.Factorys.FactoryDonacionDeDinero;
import Repositorios.RepositorioHeladeras;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class CtrlDonarViandas {
    private final List<HeladeraDTO> heladeras = RepositorioHeladeras.getInstancia().getHeladeras().stream().map(heladera -> convertirHeladeraADTO(heladera)).collect(Collectors.toList());
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200", "1234")), List.of(new WhatsApp("15 2350-2350")));
    List<EstadoVianda> estados = new ArrayList<>();

    public void setEstados() {
        if(estados.isEmpty()) {
            estados.add(EstadoVianda.NO_ENTREGADA);
            estados.add(EstadoVianda.ENTREGADA);
            estados.add(EstadoVianda.EN_TRASLADO);
            estados.add(EstadoVianda.VENCIDA);
            estados.add(EstadoVianda.RETIRADA);
        }
    }

    @GetMapping("/DonarViandas")
    public String mostrarHeladeras(Model model) {
        if(Objects.isNull(colaborador.getTarjeta())) {
            return "PedirAccesoColaborador";
        }
        System.out.println("Muestra las heladeras");
        setEstados();
        model.addAttribute("estados", estados);
        model.addAttribute("heladeras", heladeras);
        return "DonarViandas";
    }

    @PostMapping("/DonarViandas")
    public String recibirDatos (@RequestParam(value = "tipoDeVianda", defaultValue = "0") String tipoVianda,
                                @RequestParam(value = "fechaCaducidad", defaultValue = "0") LocalDate fechaCaducidad,
                                @RequestParam(value = "fechaDonacion", defaultValue = "0") LocalDate fechaDonacion,
                                @RequestParam(value = "heladera", defaultValue = "0") String idHeladeraElegida,
                                @RequestParam(value = "peso", defaultValue = "0") String peso,
                                @RequestParam(value = "calorias", defaultValue = "0") String calorias,
                                @RequestParam(value = "estado", defaultValue = "0") EstadoVianda estado,
                                /*DonacionDeViandaDTO donacionDeViandaDTO,*/
                                Model model){

        Heladera heladeraElegida = RepositorioHeladeras.getInstancia().buscarHeladeraPorId(Integer.parseInt(idHeladeraElegida));
        ViandaDTO viandaDTO = new ViandaDTO(tipoVianda, fechaCaducidad, fechaDonacion, colaborador, heladeraElegida, calorias, peso, estado);
        List<ViandaDTO> viandas = new ArrayList<>();
        viandas.add(viandaDTO);
        if(colaborador.getTarjeta().aperturaAutorizada(heladeraElegida)) {
            DonacionDeViandaDTO donacionDeViandaDTO = new DonacionDeViandaDTO(colaborador, heladeraElegida, viandas);

            BuilderDonacionDeViandas.crearDonacionAPartirDe(donacionDeViandaDTO);
            GestorDonacionDeViandas.crearContribucion(donacionDeViandaDTO);
            model.addAttribute("mensaje", "Donacion realizada con éxito!");
            return mostrarHeladeras(model);
        } else{
            model.addAttribute("mensaje", "No tiene acceso a la heladera!");
            return "Home";
        }

    }

    private HeladeraDTO convertirHeladeraADTO(Heladera heladera) {
        return new HeladeraDTO(heladera.getColaboradorACargo(), heladera.getCapacidadDeViandas(), heladera.getModelo().getNombreModelo(), heladera.getModelo().getTemperaturaMaxima(), heladera.getModelo().getTemperaturaMinima(), heladera.getUbicacion().getDireccion().getCalle(), heladera.getUbicacion().getDireccion().getAltura(), heladera.getUbicacion().getDireccion().getCodPostal(), heladera.getUbicacion().getCiudad(), heladera.getUbicacion().getNombreDelPunto(), heladera.getPuestaEnFuncionamiento());
    }
}
