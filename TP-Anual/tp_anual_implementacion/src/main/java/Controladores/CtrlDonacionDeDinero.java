package Controladores;

import Modelo.Dominio.GestionDeContribuciones.GestorDonacionDeDinero;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Factorys.FactoryDonacionDeDinero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import DTOs.DonacionDeDineroDTO;

@Controller
public class CtrlDonacionDeDinero {

    @GetMapping("/DonarDinero")
    public String donarDineroHome() {
        return "DonarDinero";
    }

    @PostMapping("/ProcesarDonacion")
    public ResponseEntity<String> crearDonacion(@RequestBody DonacionDeDineroDTO donacionDTO) {
        DonacionDeDinero nuevaDonacion = FactoryDonacionDeDinero.crearContribucionAPartirDe(donacionDTO);
        GestorDonacionDeDinero.crearContribucion(nuevaDonacion);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}