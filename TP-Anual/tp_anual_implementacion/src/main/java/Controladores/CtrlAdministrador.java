package Controladores;

import Modelo.carga_masiva.GestorCargaMasiva;
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

    @Autowired
    public CtrlAdministrador(GestorCargaMasiva gestorCargaMasiva) {
        this.gestorCargaMasiva = gestorCargaMasiva;
    }

    @GetMapping("/Administrador")
    public String mostrarPagina() { return "Administrador"; }

    @Transactional
    @PostMapping("/CargaMasiva")
    public ResponseEntity<String> recibirCSV(@RequestParam("archivoCSVCarga") MultipartFile archivo) {
        System.out.println("Archivo recibido: " + archivo.getOriginalFilename());
        try {
            String pathCSV = null;
            if (!Objects.isNull(archivo) && !archivo.isEmpty()) {
                pathCSV = DescargaDeArchivo.guardarArchivo("/CSV/", archivo);
            }

            GestorCargaMasiva.migrar(pathCSV);

            return ResponseEntity.ok("El archivo CSV fue cargado de manera exitosa.");
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
