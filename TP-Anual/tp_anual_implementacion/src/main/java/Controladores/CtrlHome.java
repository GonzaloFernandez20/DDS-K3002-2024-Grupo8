package Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CtrlHome {

    @GetMapping("/")
    public String home() {
        return "Home"; // Esto buscará un archivo index.html en src/main/resources/templates/
    }
}

