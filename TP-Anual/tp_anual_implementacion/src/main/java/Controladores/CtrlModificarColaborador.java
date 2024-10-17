package Controladores;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaHumana;

import Modelo.Dominio.persona.PersonaJuridica;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CtrlModificarColaborador {
    //COLABORADOR HARDCODEADO HASTA PODER ARMAR LA SESIÓN
    private final Colaborador colaborador = new Colaborador(new PersonaHumana("Luis", "Gómez", LocalDate.now(), new Documento(TipoDeDocumento.DNI, "43.444.444", Sexo.MASCULINO), new Direccion("Saraza", "1200", "1234")), List.of(new WhatsApp("15 2350-2350")));
    //

    @GetMapping("/ModificarColaborador")
    public String mostrarColaborador() {
        if(colaborador.getPersona() instanceof PersonaHumana) {
            return "ModificarColaboradorHumano";
        }

        if(colaborador.getPersona() instanceof PersonaJuridica) {
            return "ModificarColaboradorJuridicoCuenta";
        }

        return "Home";
    }
}
