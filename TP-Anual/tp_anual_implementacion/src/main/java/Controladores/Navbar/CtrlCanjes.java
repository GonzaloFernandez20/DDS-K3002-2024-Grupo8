package Controladores.Navbar;

import DTOs.OfertaDeUnProductoDTO;
import Modelo.Dominio.GestionDeContribuciones.GestorDeOfertaDeProductos;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.seguridad.GestorInicioDeSesion;
import Repositorios.RepositorioOfertas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CtrlCanjes {

    private final GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public CtrlCanjes(GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    private final List<OfertaDeUnProductoDTO> ofertas = RepositorioOfertas.getInstancia().getOfertas().stream().map(oferta -> convertirOfertaADTO(oferta)).collect(Collectors.toList());

    @GetMapping("/CanjearPuntos")
    public String mostrarProductosYServicios(Model model) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        System.out.println("Muestra los productos y servicios");
        model.addAttribute("ofertas", ofertas);
        model.addAttribute("puntosDelColaborador", colaborador.getPuntosAcumulados());
        return "CanjearPuntos";
    }

    @PostMapping("/CanjearPuntos")
    public String pedirCanjeDePuntos(@RequestParam("oferta") String idOferta, Model model) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        System.out.println("Pide canjear el producto con id " + idOferta);
        OfertaDeUnProducto oferta = RepositorioOfertas.getInstancia().buscarOfertaPorId(Integer.parseInt(idOferta));

        String mensaje;
        try {
            GestorDeOfertaDeProductos.canjearProducto(colaborador, oferta);
            mensaje = "¡Felicidades! Logró realizar el canje de " + oferta.getNombreOferta() + " por " + oferta.getPuntosNecesarios() + " puntos.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mensaje = "Error en el canje: " + e.getMessage();
        }

        model.addAttribute("mensaje", mensaje);

        return mostrarProductosYServicios(model);
    }

    private OfertaDeUnProductoDTO convertirOfertaADTO(OfertaDeUnProducto oferta) {
        return new OfertaDeUnProductoDTO(oferta.getNombreOferta(), oferta.getPuntosNecesarios(), oferta.getImagen(), oferta.getRubro(), oferta.getProducto().getNombreProducto(), oferta.getProducto().getStock());
    }
}
