package Controladores;

import Modelo.Dominio.GestionDeContribuciones.GestorDonacionDeDinero;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import DTOs.DonacionDeDineroDTO;

public class CtrlDonacionDeDinero {

    @PostMapping("endpoint")
    public ResponseEntity<String> crearDonacion(@RequestBody DonacionDeDineroDTO donacionDTO) {
        GestorDonacionDeDinero.crearContribucion(donacionDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}