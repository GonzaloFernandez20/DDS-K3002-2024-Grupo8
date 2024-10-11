
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import DTOs.DonacionDeDineroDTO;
import Modelo.Factorys.FactoryDonacionDeDinero;

public class CtrlDonacionDeDinero {

    @PostMapping("endpoint")
    public ResponseEntity<String> crearDonacion(@RequestBody DonacionDeDineroDTO donacionDTO) {
        
        return new ResponseEntity<>("Donacion procesada", HttpStatus.CREATED);
    }
}