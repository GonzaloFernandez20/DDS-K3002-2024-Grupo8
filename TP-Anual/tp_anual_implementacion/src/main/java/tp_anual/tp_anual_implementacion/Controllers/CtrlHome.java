package tp_anual.tp_anual_implementacion.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CtrlHome {
    @GetMapping({"/", "/Home"})
    public String home() {
        return "Home";
    }
}
