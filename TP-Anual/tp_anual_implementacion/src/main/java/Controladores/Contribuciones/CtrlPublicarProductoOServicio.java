package Controladores.Contribuciones;

import DTOs.OfertaDeUnProductoDTO;
import Modelo.Dominio.GestionDeContribuciones.GestorDeOfertaDeProductos;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.Repositories.contribucion.OfertaDeUnProductoRepository;
import Modelo.Dominio.Repositories.contribucion.ProductoRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.Dominio.contribucion.Rubro;

import Modelo.Mappers.OfertaMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CtrlPublicarProductoOServicio {
    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final OfertaDeUnProductoRepository ofertaRepository;
    private final ProductoRepository productoRepository;
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    public CtrlPublicarProductoOServicio(GestorInicioDeSesion gestorInicioDeSesion, OfertaDeUnProductoRepository ofertaRepository, ProductoRepository productoRepository, ColaboradorRepository colaboradorRepository) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.ofertaRepository = ofertaRepository;
        this.productoRepository = productoRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    private List<Rubro> obtenerTodosLosRubros() {
        List<Rubro> todosLosRubros = new ArrayList<>();

        todosLosRubros.add(Rubro.GASTRONOMIA);
        todosLosRubros.add(Rubro.ELECTRONICA);
        todosLosRubros.add(Rubro.ARTICULOS_PARA_EL_HOGAR);
        todosLosRubros.add(Rubro.INDUMENTARIA);
        todosLosRubros.add(Rubro.DEPORTES);
        todosLosRubros.add(Rubro.JUGUETERIA);
        todosLosRubros.add(Rubro.LIBRERIA);
        todosLosRubros.add(Rubro.SALUD_Y_BELLEZA);
        todosLosRubros.add(Rubro.AUTOMOTRIZ);
        todosLosRubros.add(Rubro.MUEBLES);
        todosLosRubros.add(Rubro.TECNOLOGIA);
        todosLosRubros.add(Rubro.FERRETERIA);
        todosLosRubros.add(Rubro.ALIMENTOS_Y_BEBIDAS);
        todosLosRubros.add(Rubro.VIAJES_Y_TURISMO);
        todosLosRubros.add(Rubro.JOYERIA);

        return todosLosRubros;
    }

    @GetMapping("/PublicarProductoOServicio")
    public String mostrarRubros(Model model) {
        model.addAttribute("rubros", obtenerTodosLosRubros());

        return "PublicarProductoOServicio";
    }

    @PostMapping("/PublicarProductoOServicio")
    @Transactional
    public String publicarProductoOServicio(@ModelAttribute OfertaDeUnProductoDTO ofertaDTO, @RequestParam("imagen") MultipartFile imagen,
                                            Model model) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();
        ofertaDTO.setLinkDeImagenAPartirDeArchivo(imagen);

        OfertaDeUnProducto ofertaDeUnProducto = OfertaMapper.crearOfertaAPartirDe(colaborador, ofertaDTO);
        GestorDeOfertaDeProductos.crearContribucion(colaborador, ofertaDeUnProducto);

        System.out.println("Rubro: " + ofertaDeUnProducto.getRubro());
        System.out.println("Nombre oferta: " + ofertaDeUnProducto.getNombreOferta());
        System.out.println("Nombre producto: " + ofertaDeUnProducto.getProducto().getNombreProducto());
        System.out.println("Cant puntos: " + ofertaDeUnProducto.getPuntosNecesarios());
        System.out.println("Stock: " + ofertaDeUnProducto.getProducto().getStock());

        ofertaRepository.save(ofertaDeUnProducto);
        productoRepository.save(ofertaDeUnProducto.getProducto());
        colaboradorRepository.save(colaborador);
        model.addAttribute("mensaje", "¡Felicitaciones! La oferta de " + ofertaDeUnProducto.getProducto().getNombreProducto() + " se realizó exitosamente.");

        return mostrarRubros(model);
    }
}
