package Controladores;

import Modelo.Dominio.GestionDeContribuciones.GestorDeOfertaDeProductos;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.Dominio.contribucion.Producto;
import Modelo.Dominio.contribucion.Rubro;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CtrlPublicarProductoOServicio {
    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200", "1234")), List.of(new WhatsApp("15 2350-2350")));
    //

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
    public String publicarProductoOServicio(@RequestParam(value = "rubro", defaultValue = "GASTRONOMIA") String stringRubro,
                                            @RequestParam(name = "nombre-oferta") String nombreOferta,
                                            @RequestParam(name = "nombre-producto") String nombreProducto,
                                            @RequestParam(name = "cant-puntos", defaultValue = "0") double cantPuntos,
                                            @RequestParam(name = "imagen", required = false) MultipartFile imagen,
                                            @RequestParam(name = "stock", defaultValue = "0") int stock,
                                            Model model) {

        String pathImagen = null;
        if (!Objects.isNull(imagen) && !imagen.isEmpty()) {
            pathImagen = DescargaDeArchivo.guardarArchivo("/fotosProductosOServicios/", imagen);
        }

        Rubro rubro = Rubro.valueOf(stringRubro);
        Producto producto = new Producto(nombreProducto, stock);
        OfertaDeUnProducto ofertaDeUnProducto = new OfertaDeUnProducto(colaborador, nombreOferta, cantPuntos, pathImagen, rubro, producto);
        GestorDeOfertaDeProductos.crearContribucion(colaborador, ofertaDeUnProducto);

        System.out.println("Rubro: " + rubro);
        System.out.println("Nombre oferta: " + nombreOferta);
        System.out.println("Nombre producto: " + nombreProducto);
        System.out.println("Cant puntos: " + cantPuntos);
        System.out.println("Path imagen: " + pathImagen);
        System.out.println("Stock: " + stock);

        model.addAttribute("mensaje", "¡Felicitaciones! La oferta de " + nombreOferta + " se realizó exitosamente.");

        return mostrarRubros(model);
    }
}
