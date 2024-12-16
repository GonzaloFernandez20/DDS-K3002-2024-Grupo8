package Controladores.Navbar;

import DTOs.OfertaDeUnProductoDTO;
import Modelo.Dominio.GestionDeContribuciones.GestorDeOfertaDeProductos;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.seguridad.GestorInicioDeSesion;

import Repositories.colaborador.ColaboradorRepository;
import Repositories.contribucion.OfertaDeUnProductoRepository;
import Repositories.contribucion.ProductoRepository;

import Servicios_Externos_APIs.NotificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CtrlCanjes {

    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final ColaboradorRepository colaboradorRepository;
    private final OfertaDeUnProductoRepository ofertaDeUnProductoRepository;
    private final ProductoRepository productoRepository;
    private NotificacionService notificacionService;

    @Autowired
    public CtrlCanjes(GestorInicioDeSesion gestorInicioDeSesion, ColaboradorRepository colaboradorRepository, OfertaDeUnProductoRepository ofertaDeUnProductoRepository, ProductoRepository productoRepository, NotificacionService notificacionService) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.colaboradorRepository = colaboradorRepository;
        this.ofertaDeUnProductoRepository = ofertaDeUnProductoRepository;
        this.productoRepository = productoRepository;
        this.notificacionService = notificacionService;
    }

    @GetMapping("/CanjearPuntos")
    public String mostrarProductosYServicios(Model model) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        System.out.println("Muestra los productos y servicios");

        List<OfertaDeUnProducto> ofertas = ofertaDeUnProductoRepository.findAll();
        model.addAttribute("ofertas", ofertas.stream().map(this::convertirOfertaADTO).collect(Collectors.toList()));

        model.addAttribute("puntosDelColaborador", colaborador.getPuntosAcumulados());
        return "CanjearPuntos";
    }

    @PostMapping("/CanjearPuntos")
    public String pedirCanjeDePuntos(@RequestParam("oferta") String idOferta, Model model) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        System.out.println("Pide canjear el producto con id " + idOferta);
        OfertaDeUnProducto oferta = ofertaDeUnProductoRepository.findById(Integer.parseInt(idOferta))
                .orElseThrow(() -> new IllegalArgumentException("La oferta con ID " + idOferta + " no existe."));

        String mensaje;
        try {
            GestorDeOfertaDeProductos.canjearProducto(colaborador, oferta);
            colaboradorRepository.save(colaborador);
            productoRepository.save(oferta.getProducto());
            mensaje = "¡Felicidades! Logró realizar el canje de " + oferta.getNombreOferta() + " por " + oferta.getPuntosNecesarios() + " puntos.";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mensaje = "Error en el canje: " + e.getMessage();
        }

        model.addAttribute("mensaje", mensaje);

        // Enviar una notificación
        notificacionService.sendNotificacionToColaborador(gestorInicioDeSesion.obtenerColaboradorPorID(), mensaje);
           
        return mostrarProductosYServicios(model);
    }

    private OfertaDeUnProductoDTO convertirOfertaADTO(OfertaDeUnProducto oferta) {
        OfertaDeUnProductoDTO ofertaDeUnProductoDTO =  new OfertaDeUnProductoDTO(oferta.getNombreOferta(), oferta.getPuntosNecesarios(), oferta.getPathImagen(), oferta.getRubro(), oferta.getProducto().getNombreProducto(), oferta.getProducto().getStock());
        ofertaDeUnProductoDTO.setIdOferta(oferta.getId_contribucion());
        return ofertaDeUnProductoDTO;
    }
}
