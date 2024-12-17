package Controladores.Contribuciones;

import Modelo.Dominio.GestionDeContribuciones.GestorDonacionDeDinero;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Mappers.DonacionDeDineroMapper;

import Modelo.seguridad.GestorInicioDeSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import DTOs.DonacionDeDineroDTO;

@Controller
public class CtrlDonacionDeDinero {
    //DEPENDENCIAS: repositorios y gestores ----------------------------------------------------------------------------------
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final GestorDonacionDeDinero gestorDonacionDeDinero;

    @Autowired
    public CtrlDonacionDeDinero(GestorInicioDeSesion gestorInicioDeSesion,
                                GestorDonacionDeDinero gestorDonacionDeDinero) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.gestorDonacionDeDinero = gestorDonacionDeDinero;
    }

    //GET MAPPING -------------------------------------------------------------------------------------------------------------
    @GetMapping("/DonarDinero")
    public String donarDineroHome() {
        return "DonarDinero";
    }

    //POST MAPPING -------------------------------------------------------------------------------------------------------------
    @PostMapping("/ProcesarDonacionDeDinero")
    public ResponseEntity<String> recibirDonacionDinero(@RequestBody DonacionDeDineroDTO donacionDTO) {
        DonacionDeDinero nuevaDonacion = procesarDTO(donacionDTO);
        gestorDonacionDeDinero.procesarDineroDonado(nuevaDonacion);
        return ResponseEntity.ok().body("Donación de dinero realizada con éxito");
    }

    private DonacionDeDinero procesarDTO(DonacionDeDineroDTO donacionDTO) {
        return DonacionDeDineroMapper.crearContribucionAPartirDe(donacionDTO, gestorInicioDeSesion.obtenerColaboradorPorID());
    }
}