package Controladores.Navbar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CtrlMapa {

    @GetMapping("/Mapa")
    public String mapaHome() {
        return "Mapa";
    }
}
