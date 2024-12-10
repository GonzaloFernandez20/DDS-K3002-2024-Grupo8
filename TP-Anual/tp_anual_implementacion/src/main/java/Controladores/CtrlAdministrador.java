package Controladores;

import Modelo.carga_masiva.GestorCargaMasiva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String mostrarPagina(Model model) { return "Administrador"; }

    @PostMapping("/Administrador")
    public ResponseEntity<String> recibirCSV(@RequestBody MultipartFile archivo) {
        String pathCSV = null;
        if (!Objects.isNull(archivo) && !archivo.isEmpty()) {
            pathCSV = DescargaDeArchivo.guardarArchivo("/CSV/", archivo);
        }
        System.out.println("Path pathCSV: " + pathCSV);

        GestorCargaMasiva.migrar(pathCSV);

        return ResponseEntity.ok("El archivo CSV fue cargado de manera exitosa.");
    }
}
