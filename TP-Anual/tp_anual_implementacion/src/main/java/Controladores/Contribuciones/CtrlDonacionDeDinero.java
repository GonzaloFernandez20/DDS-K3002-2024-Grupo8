package Controladores.Contribuciones;

import Modelo.Dominio.GestionDeContribuciones.GestorDonacionDeDinero;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Mappers.FactoryDonacionDeDinero;

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

    @PostMapping("/ProcesarDonacionDeDinero")
    public ResponseEntity<String> crearDonacion(@RequestBody DonacionDeDineroDTO donacionDTO) {
        DonacionDeDinero nuevaDonacion = FactoryDonacionDeDinero.crearContribucionAPartirDe(donacionDTO);
        GestorDonacionDeDinero.crearContribucion(nuevaDonacion);
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain; charset=UTF-8")
                .body("Usuario y contraseña validados exitosamente.");
    }
}