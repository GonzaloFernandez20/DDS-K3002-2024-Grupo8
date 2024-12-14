package Controladores;

import DTOs.ColaboradorHumanoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;


import Modelo.Mappers.ColabHumanoMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import Modelo.seguridad.SesionActiva.GeneradorDeCookie;
import Modelo.seguridad.SesionActiva.Usuario;
import Repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CtrlModificarColaborador {

    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final UsuariosRepository usuariosRepository;

    @Autowired
    public CtrlModificarColaborador(GestorInicioDeSesion gestorInicioDeSesion, UsuariosRepository usuariosRepository) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.usuariosRepository = usuariosRepository;
    }

    @GetMapping("/ModificarColaborador")
    public String mostrarColaborador(Model model) {
        switch (tipoPersonaDelColaborador()) {
            case "PersonaHumana":
                model.addAttribute("tiposDeDocumento", tiposDeDNI());
                model.addAttribute("colaborador", datosColaboradorHumano());
                return "ModificarColaboradorHumano";
            case "PersonaJuridica":
                return "ModificarColaboradorJuridicoCuenta";
            default:
                return "Home";
        }
    }

    @PostMapping("/ModificarColaborador/Humano")
    public ResponseEntity<Void> actualizarDatosDeColaboradorH(@RequestBody ColaboradorHumanoDTO colaboradorHumanoDTO){
        try {
            Usuario usuario = ColabHumanoMapper.actualizarDatosDeColaboradorHumanoAPartirDe(colaboradorHumanoDTO);
            usuariosRepository.save(usuario);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ModificarColaboradorPremios")
    public String mostrarPremios(Model model) {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        model.addAttribute("tieneTarjeta", !Objects.isNull(colaborador.getTarjeta()));
        model.addAttribute("tipoColaborador", tipoPersonaDelColaborador());
        model.addAttribute("puntos", colaborador.getPuntosAcumulados());

        return "ModificarColaboradorPremios";
    }


    @PostMapping("/ModificarColaborador/EliminarCuenta")
    public ResponseEntity<Void> eliminarUsuario(){
        try {
            Usuario usuario = gestorInicioDeSesion.obtenerUsuarioDeSesion();
            usuariosRepository.delete(usuario);
            ResponseCookie cookie = GeneradorDeCookie.eliminarCookie();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* ---------------------------------------------------------------------------------- */

    private String tipoPersonaDelColaborador() {
        Colaborador colaborador = gestorInicioDeSesion.obtenerColaboradorPorID();

        if(colaborador.getPersona() instanceof PersonaHumana) {
            return "PersonaHumana";
        }

        if(colaborador.getPersona() instanceof PersonaJuridica) {
            return "PersonaJuridica";
        }

        return "";
    }

    private Object datosColaboradorHumano() {
        Usuario usuarioDeSesion = gestorInicioDeSesion.obtenerUsuarioDeSesion();
        Colaborador colaboradorDeSesion = gestorInicioDeSesion.obtenerColaboradorPorID();
        PersonaHumana personaHumana = (PersonaHumana) colaboradorDeSesion.getPersona();

        ColaboradorHumanoDTO colaborador = new ColaboradorHumanoDTO(
                usuarioDeSesion.getUsuario(),
                usuarioDeSesion.getContrasenia(),
                personaHumana.getNombre(),
                personaHumana.getApellido(),
                personaHumana.getFechaDeNacimiento(),
                personaHumana.getDocumento().getTipo(),
                personaHumana.getDocumento().getNumero(),
                personaHumana.getDocumento().getSexo(),
                personaHumana.getDireccion().getCalle(),
                personaHumana.getDireccion().getAltura(),
                null, null, false, false,
                !Objects.isNull(colaboradorDeSesion.getTarjeta())
        );
        return colaborador;
    }

    public List<TipoDeDocumento> tiposDeDNI() {
        List<TipoDeDocumento> tiposDeDocumento = new ArrayList<>();

        tiposDeDocumento.add(TipoDeDocumento.DNI);
        tiposDeDocumento.add(TipoDeDocumento.LE);
        tiposDeDocumento.add(TipoDeDocumento.LC);
        tiposDeDocumento.add(TipoDeDocumento.CI);
        tiposDeDocumento.add(TipoDeDocumento.PASAPORTE);

        return tiposDeDocumento;
    }
}