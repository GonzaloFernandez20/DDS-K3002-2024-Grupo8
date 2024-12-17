package Controladores.Contribuciones;

import DTOs.DonacionDeViandaDTO;
import DTOs.HeladeraSeleccionDTO;
import Modelo.Dominio.Accesos_a_heladeras.GestorDePermisosDeApertura;
import Modelo.Dominio.contribucion.*;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Mappers.HeladeraSeleccionMapper;
import Modelo.Mappers.DonacionDeViandasMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import Repositories.heladera.HeladeraRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
public class CtrlDonacionViandas {

    //Dependencias ----------------------------------------------------------------------------------------------------
    private final HeladeraRepository repositorioHeladeras;
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final GestorDePermisosDeApertura gestorDePermisosDeApertura;

    @Autowired
    public CtrlDonacionViandas(HeladeraRepository repositorioHeladeras,
                               GestorInicioDeSesion gestorInicioDeSesion,
                               GestorDePermisosDeApertura gestorDePermisosDeApertura) {
        this.repositorioHeladeras = repositorioHeladeras;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.gestorDePermisosDeApertura = gestorDePermisosDeApertura;

    }

    //GET MAPPING ---------------------------------------------------------------------------------------------------------------
    @GetMapping("/DonarViandas")
    public String mostrarHeladeras(Model model) {
        if(Objects.isNull(gestorInicioDeSesion.obtenerColaboradorPorID().getTarjeta())) {
            log.info("El colaborador ID:{} no tiene una tarjeta de acceso a heladeras, requerimiento para poder donarviandas",
                    gestorInicioDeSesion.obtenerColaboradorPorID().getId_colaborador());
            return "PedirTarjetaColaborador";
        }
        List<HeladeraSeleccionDTO> heladeras = repositorioHeladeras.traerHeladerasActivasEnElSistema().stream().
                map(heladera -> HeladeraSeleccionMapper.convertirEnHeladeraSeleccionDTO(heladera)).collect(Collectors.toList());
        model.addAttribute("heladeras", heladeras);
        return "DonarViandas";
    }

    //POST MAPPING ---------------------------------------------------------------------------------------------------------------
    @Transactional
    @PostMapping("/DonarViandas")
    public  ResponseEntity<String> donarVianda(@RequestBody DonacionDeViandaDTO donacionDTO){
        try {
            DonacionDeViandas nuevaDonacion = procesarDTO(donacionDTO);
            gestorDePermisosDeApertura.generarPermisoDeDonacion(nuevaDonacion);
            return ResponseEntity.ok("Permiso de donacion de viandas generado con éxito, tiene 3 horas para dejar las viandas en la heladera antes de que venza el permiso de apertura!");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

    private DonacionDeViandas procesarDTO(DonacionDeViandaDTO dto){
        Optional<Heladera> heladeraElegida = repositorioHeladeras.findById(dto.getHeladeraID());
        if (heladeraElegida.isPresent()) {
            return DonacionDeViandasMapper.crearDonacionDeViandasAPartirDe(dto, heladeraElegida.get(), gestorInicioDeSesion.obtenerColaboradorPorID());
        } else {
            throw new RuntimeException("Heladera no encontrada con ID: " + dto.getHeladeraID());
        }
    }

}
