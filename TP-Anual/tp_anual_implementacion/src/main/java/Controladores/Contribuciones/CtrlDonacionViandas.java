package Controladores.Contribuciones;

import DTOs.DonacionDeViandaDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.contribucion.*;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Mappers.HeladeraSeleccionMapper;
import Modelo.Mappers.DonacionDeViandasMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CtrlDonacionViandas {

    private final Repositories.heladera.HeladeraRepository repositorioHeladeras;
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final GestorDePermisosDeApertura gestorDePermisosDeApertura;
    private final Repositories.contribucion.DonacionDeViandasRepository donacionDeViandasRepository;

    @Autowired
    public CtrlDonacionViandas(Repositories.heladera.HeladeraRepository repositorioHeladeras, GestorInicioDeSesion gestorInicioDeSesion,
                              GestorDePermisosDeApertura gestorDePermisosDeApertura,
                               Repositories.contribucion.DonacionDeViandasRepository donacionDeViandasRepository) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.gestorDePermisosDeApertura = gestorDePermisosDeApertura;
        this.donacionDeViandasRepository = donacionDeViandasRepository;
    }

    private List<HeladeraSeleccionDTO> heladeras;

    List<EstadoVianda> estados = new ArrayList<>();


    public void setEstados() {
        if(estados.isEmpty()) {
            estados.add(EstadoVianda.NO_ENTREGADA);
            estados.add(EstadoVianda.ENTREGADA);
            estados.add(EstadoVianda.EN_TRASLADO);
            //estados.add(EstadoVianda.VENCIDA);
            //estados.add(EstadoVianda.RETIRADA);
        }
    }

    @GetMapping("/DonarViandas")
    public String mostrarHeladeras(Model model) {
        if(Objects.isNull(gestorInicioDeSesion.obtenerColaboradorPorID().getTarjeta())) {
            return "PedirTarjetaColaborador";
        }
        heladeras = repositorioHeladeras.traerHeladerasActivasEnElSistema().stream().
                map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());
        setEstados();
        model.addAttribute("heladeras", heladeras);
        model.addAttribute("estados", estados);
        return "DonarViandas";
    }

    private DonacionDeViandas procesarDTO(DonacionDeViandaDTO dto){
        Optional<Heladera> heladeraElegida = repositorioHeladeras.findById(dto.getHeladeraID());
        if (heladeraElegida.isPresent()) {
            return DonacionDeViandasMapper.crearDonacionDeViandasAPartirDe(dto, heladeraElegida.get(), gestorInicioDeSesion.obtenerColaboradorPorID());
        } else {
            throw new RuntimeException("Heladera no encontrada con ID: " + dto.getHeladeraID());
        }
    }

    @Transactional
    @PostMapping("/DonarViandas")
    public  ResponseEntity<String> donarVianda(@RequestBody DonacionDeViandaDTO donacionDTO){
        if (donacionDTO == null || donacionDTO.getViandasDTO() == null || donacionDTO.getViandasDTO().isEmpty()) {
            throw new RuntimeException("La donación o la lista de viandas está vacía");
        }

        try {
            DonacionDeViandas nuevaDonacion = procesarDTO(donacionDTO);
            donacionDeViandasRepository.save(nuevaDonacion);
            gestorDePermisosDeApertura.generarPermisoDeDonación(nuevaDonacion);
            return ResponseEntity.ok("Donacion realizada con éxito!");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

}
