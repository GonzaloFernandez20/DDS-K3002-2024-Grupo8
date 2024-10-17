package Controladores.Navbar;
import Modelo.Dominio.GestionDeContribuciones.FormaDeContribuciones;
import Modelo.Dominio.GestionDeContribuciones.ValidadorRequisitosContribucion;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Modelo.Dominio.GestionDeContribuciones.FormaDeContribuciones.*;
import static Modelo.Dominio.documentacion.Sexo.MASCULINO;
import static Modelo.Dominio.documentacion.TipoDeDocumento.DNI;

@Controller
public class CtrlSeleccionDeContribucion {
    @GetMapping("/Colaborar")
    public String Colaborar(@RequestParam("value") String tipoDeContribucion, RedirectAttributes redirectAttributes) {
        System.out.println("llegue");
        Documento documento = new Documento(DNI, "12344567", MASCULINO);
        Direccion direccion = new Direccion("Corrientes", "1000", "1234");
        MedioDeContacto medioDeContacto = new WhatsApp("1529102222");
        List <MedioDeContacto> medios = new ArrayList<>();
        medios.add(medioDeContacto);
        PersonaHumana personaHumana = new PersonaHumana("Francisco", "Perez", LocalDate.now(), documento, direccion);
        Colaborador colaborador = new Colaborador(personaHumana, medios);

        FormaDeContribuciones formaDeContribucion = validarTipoDeAcceso(tipoDeContribucion);

        //ValidadorRequisitosContribucion validadorRequisitosContribucion = new ValidadorRequisitosContribucion();
        System.out.println("Tipo de contribución recibido: " + tipoDeContribucion);
        try {
            ValidadorRequisitosContribucion.validarRequisitos(colaborador, formaDeContribucion);
            redirectAttributes.addFlashAttribute("mensaje", "Puede colaborar en " + tipoDeContribucion);
            return "redirect:/" + tipoDeContribucion;
            //model.addAttribute("mensaje", "Puede colaborar en " + tipoDeContribucion);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "No puede colaborar en " + tipoDeContribucion);
            return "redirect:/error";
            //model.addAttribute("mensaje", "No puede colaborar: " + e.getMessage());
        }
    }

    public FormaDeContribuciones validarTipoDeAcceso(String tipoDeContribucion) {
        switch (tipoDeContribucion) {
            case "EntregarTarjetasDeAcceso":
                return CONTRIBUCION_HUMANA;
            case "DonarViandas:":
            case "DistribuirViandas":
                return CONTRIBUCION_CON_APERTURA;
            case "HacerseCargoDeUnaHeladera":
            case "PublicarProductoOServicio":
                return CONTRIBUCION_JURIDICA;
            case "DonarDinero":
                return CONTRIBUCION_SIN_RESTRICCION;
            default:
                break;
        }

        return null;
    }
}