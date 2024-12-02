package Controladores.Navbar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CtrlFooter {
    @GetMapping({"/Footer"})
    public String footer() {
        return "Footer";
    }
}
