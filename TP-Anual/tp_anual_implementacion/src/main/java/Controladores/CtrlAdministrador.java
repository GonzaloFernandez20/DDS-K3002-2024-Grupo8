package Controladores;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.carga_masiva.GestorCargaMasiva;
import Modelo.seguridad.GestorInicioDeSesion;
import Utils.DescargaDeArchivo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
public class CtrlAdministrador {

    GestorCargaMasiva gestorCargaMasiva;
    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlAdministrador(GestorCargaMasiva gestorCargaMasiva, GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorCargaMasiva = gestorCargaMasiva;
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    @GetMapping("/Administrador")
    public String mostrarPagina() {
        Colaborador colaboradorActual = gestorInicioDeSesion.obtenerColaboradorPorID();

        if (colaboradorActual != null) {
            return "AccesoNoPermitido";
        }
        else return "Administrador";
    }

    @Transactional
    @PostMapping("/CargaMasiva")
    public ResponseEntity<String> recibirCSV(@RequestParam("archivoCSVCarga") MultipartFile archivo) {
        System.out.println("Archivo recibido: " + archivo.getOriginalFilename());
        try {
            String pathCSV = null;
            if (!Objects.isNull(archivo) && !archivo.isEmpty()) {
                pathCSV = DescargaDeArchivo.guardarArchivo("CSV", archivo);
            }

            GestorCargaMasiva.migrar(pathCSV);

            return ResponseEntity.ok("El archivo CSV fue cargado de manera exitosa.");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
