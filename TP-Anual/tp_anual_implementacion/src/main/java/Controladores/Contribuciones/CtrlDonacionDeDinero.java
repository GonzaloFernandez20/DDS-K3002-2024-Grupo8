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
    //DEPENDENCIAS: repositorios y gestores --------------------------------------
    private final GestorInicioDeSesion gestorInicioDeSesion;
    @Autowired
    public CtrlDonacionDeDinero(GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    @GetMapping("/DonarDinero")
    public String donarDineroHome() {
        return "DonarDinero";
    }

    @PostMapping("/ProcesarDonacionDeDinero")
    public ResponseEntity<String> recibirDonacionDinero(@RequestBody DonacionDeDineroDTO donacionDTO) {
        DonacionDeDinero nuevaDonacion = procesarDTO(donacionDTO);
        GestorDonacionDeDinero.procesarDineroDonado(nuevaDonacion);
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain; charset=UTF-8")
                .body("Usuario y contraseña validados exitosamente.");
    }

    private DonacionDeDinero procesarDTO(DonacionDeDineroDTO donacionDTO) {
        return DonacionDeDineroMapper.crearContribucionAPartirDe(donacionDTO, gestorInicioDeSesion.obtenerColaboradorPorID());
    }
}