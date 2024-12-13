package Controladores.Navbar;


import DTOs.HeladeraEnMapaDTO;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Mappers.HeladeraEnMapaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CtrlMapa {

    private final HeladeraRepository heladeraRepository;

    @Autowired
    public CtrlMapa(HeladeraRepository heladeraRepository) {
        this.heladeraRepository = heladeraRepository;
    }

    @GetMapping("/Mapa")
    public String mapaHome(Model model) {
        model.addAttribute("heladeras", traerHeladerasActivas());
        return "Mapa";
    }

    @GetMapping("/heladerasEnElMapa")
    public ResponseEntity<List<HeladeraEnMapaDTO>> heladerasEnElMapa() {
        return ResponseEntity.ok(traerHeladerasActivas());
    }

    private List<HeladeraEnMapaDTO> traerHeladerasActivas() {
        return heladeraRepository.traerHeladerasActivasEnElSistema().stream().map(heladera -> HeladeraEnMapaMapper.convertirEnHeladeraEnMapaDTO(heladera)).toList();
    }
}