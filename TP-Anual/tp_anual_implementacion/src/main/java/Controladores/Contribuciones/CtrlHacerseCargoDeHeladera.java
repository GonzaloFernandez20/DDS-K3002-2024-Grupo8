package Controladores.Contribuciones;

import DTOs.HeladeraDTO;
import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Factorys.BuilderHeladera;
import Servicios_Externos_APIs.API.APIRequester;
import Servicios_Externos_APIs.API.ResponseRecomendacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class CtrlHacerseCargoDeHeladera {

    @GetMapping("/HacerseCargoDeUnaHeladera")
    public String HacerseCargoDeUnaHeladera() {
        return "HacerseCargoDeUnaHeladera";
    }

    @GetMapping("/RecomendacionColocacion")
    public String RecomedacionColocacion() {
        return "RecomendacionColocacion";
    }


    @PostMapping("/FormularioDeHeladera")
    public ResponseEntity<Void> formularioDeHeladera(@RequestBody HeladeraDTO heladeraDTO) {
        Heladera nuevaHeladera = BuilderHeladera.crearHeladeraAPartirDe(heladeraDTO);
        //Esto creo no lo deberia hacer el controlador pero de momento queda aca
        Contribucion nuevaContribucion = new HacerseCargoDeHeladera(null, nuevaHeladera);
        nuevaContribucion.procesarLaContribucion();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/ObtenerPuntosRecomendados")
    public ResponseEntity<List<PuntoEnElMapa>> recomendarPuntos(@RequestParam double latitud,
                                                 @RequestParam double longitud,
                                                 @RequestParam int radio) throws IOException {
        ResponseRecomendacion puntos = APIRequester.getInstancia().obtenerPuntosRecomendados(latitud, longitud, radio);
        List<PuntoEnElMapa> puntosRecomendados = puntos.getPosiblesPuntosDeColocacion();
        return ResponseEntity.status(HttpStatus.OK).body(puntosRecomendados);
    }
}
