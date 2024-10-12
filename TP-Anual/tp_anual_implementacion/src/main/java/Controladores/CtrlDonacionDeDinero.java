package Controladores;

import Modelo.Dominio.GestionDeContribuciones.GestorDonacionDeDinero;
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




    public ResponseEntity<String> crearDonacion(@RequestBody DonacionDeDineroDTO donacionDTO) {
        GestorDonacionDeDinero.crearContribucion(donacionDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}