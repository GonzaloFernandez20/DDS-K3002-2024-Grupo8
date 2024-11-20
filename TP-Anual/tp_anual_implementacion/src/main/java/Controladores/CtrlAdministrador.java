package Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
public class CtrlAdministrador {
    @GetMapping("/Administrador")
    public String mostrarPagina(Model model) { return "Administrador"; }

    @PostMapping("/Administrador")
    public String recibirSeleccion(@RequestParam(name = "archivoCSVCarga", required = false) MultipartFile archivo,
                                   Model model) {
        String pathImagen = null;
        if (!Objects.isNull(archivo) && !archivo.isEmpty()) {
            pathImagen = DescargaDeArchivo.guardarArchivo("/CSV/", archivo);
        }
        System.out.println("Path imagen: " + pathImagen);
        model.addAttribute("mensaje", "¡Gracias! El archivo fue recibido correctamente.");
        return "Administrador";
    }
}
