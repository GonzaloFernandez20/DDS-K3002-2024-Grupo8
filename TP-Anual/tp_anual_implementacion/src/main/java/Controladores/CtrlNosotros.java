package Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CtrlNosotros {
    @GetMapping("/Nosotros")
    public String nosotros() {
        return "Nosotros";
    }
}
