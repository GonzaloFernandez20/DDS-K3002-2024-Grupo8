package Controladores;

import DTOs.OfertaDeUnProductoDTO;
import Modelo.Dominio.GestionDeContribuciones.GestorDeOfertaDeProductos;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import Repositorios.RepositorioOfertas;

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
    private final List<OfertaDeUnProductoDTO> ofertas = RepositorioOfertas.getInstancia().getOfertas().stream().map(oferta -> convertirOfertaADTO(oferta)).collect(Collectors.toList());

    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200", "1234")), List.of(new WhatsApp("15 2350-2350")));
    //

    @GetMapping("/CanjearPuntos")
    public String mostrarProductosYServicios(Model model) {
        System.out.println("Muestra los productos y servicios");
        model.addAttribute("ofertas", ofertas);
        model.addAttribute("puntosDelColaborador", colaborador.getPuntosAcumulados());
        return "CanjearPuntos";
    }

    @PostMapping("/CanjearPuntos")
    public String pedirCanjeDePuntos(@RequestParam("oferta") String idOferta, Model model) {
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
        return new OfertaDeUnProductoDTO(oferta.getNombreOferta(), oferta.getPuntosNecesarios(), oferta.getLinkDeImagen(), oferta.getRubro(), oferta.getProducto().getNombreProducto(), oferta.getProducto().getStock());
    }
}
