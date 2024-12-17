package Controladores;

import DTOs.ColaboradorHumanoDTO;
import DTOs.ColaboradorJuridicoDTO;
import DTOs.MedioDeContactoDTO;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona.PersonaJuridica;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Mappers.ColabHumanoMapper;
import Modelo.Mappers.ColabJuridicoMapper;
import Modelo.seguridad.GestorInicioDeSesion;
import Modelo.seguridad.SesionActiva.GeneradorDeCookie;
import Modelo.seguridad.SesionActiva.Usuario;
import Modelo.seguridad.SesionActiva.UtilsJWT;
import Repositories.UsuariosRepository;
import Repositories.colaborador.ColaboradorRepository;
import Repositories.medios_de_contacto.MedioDeContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class CtrlModificarColaborador {

    private final GestorInicioDeSesion gestorInicioDeSesion;
    private final UsuariosRepository usuariosRepository;
    private final MedioDeContactoRepository medioDeContactoRepository;
    private final ColaboradorRepository colaboradorRepository;

    @Autowired
    public CtrlModificarColaborador(GestorInicioDeSesion gestorInicioDeSesion, UsuariosRepository usuariosRepository, MedioDeContactoRepository medioDeContactoRepository, ColaboradorRepository colaboradorRepository) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
        this.usuariosRepository = usuariosRepository;
        this.medioDeContactoRepository = medioDeContactoRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    @GetMapping("/ModificarColaborador")
    public String mostrarColaborador(Model model, @CookieValue(name = "token", required = false) String token) {

        if (tipoPersonaDelColaborador().isEmpty()){
            return "PedirElegirColaborador";
        }

        else switch (tipoPersonaDelColaborador()) {
            case "PersonaHumana":
                model.addAttribute("sexos", sexos());
                model.addAttribute("tiposDeDocumento", tiposDeDNI());
                model.addAttribute("colaborador", datosColaboradorHumano());
                model.addAttribute("medios", medios());
                return "ModificarColaboradorHumano";
            case "PersonaJuridica":
                model.addAttribute("tiposDeOrganizacion",tiposDeOrganizacion());
                model.addAttribute("colaborador", datosColaboradorJuridico());
                model.addAttribute("medios", medios());
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
            ResponseCookie cookie = generarCookieParaLaModificacionDeUsuario(usuario);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/ModificarColaborador/Juridico")
    public ResponseEntity<Void> actualizarDatosDeColaboradorJ(@RequestBody ColaboradorJuridicoDTO colaboradorJuridicoDTO){
        try {
            Usuario usuario = ColabJuridicoMapper.actualizarDatosDeColaboradorJuridicoAPartirDe(colaboradorJuridicoDTO);
            usuariosRepository.save(usuario);

            ResponseCookie cookie = generarCookieParaLaModificacionDeUsuario(usuario);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
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

    @PostMapping("/ModificarColaborador/TipoPersona")
    public ResponseEntity<Void> setearTipoDePersona(@RequestParam String tipoPersona){
        try {
            Usuario usuario = gestorInicioDeSesion.obtenerUsuarioDeSesion();

            Colaborador colaborador = usuario.getColaborador();
            if(tipoPersona.equals("humana")){
                colaborador.setPersona(new PersonaHumana());
            } else{
                colaborador.setPersona(new PersonaJuridica());
            }

            usuariosRepository.save(usuario);

            ResponseCookie cookie = generarCookieParaLaModificacionDeUsuario(usuario);

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
        List<MedioDeContacto> mediosDeContactos = colaboradorDeSesion.getMediosDeContacto();
        List<MedioDeContactoDTO> mediosDeContactoDTO = mediosDeContactos.stream()
                .map(medio -> new MedioDeContactoDTO(medio.getTipo(), medio.getValor()))
                .toList();

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
                mediosDeContactoDTO,
                !Objects.isNull(colaboradorDeSesion.getTarjeta())
        );
        return colaborador;
    }

    private Object datosColaboradorJuridico() {
        Usuario usuarioDeSesion = gestorInicioDeSesion.obtenerUsuarioDeSesion();
        Colaborador colaboradorDeSesion = gestorInicioDeSesion.obtenerColaboradorPorID();
        PersonaJuridica personaJuridica = (PersonaJuridica) colaboradorDeSesion.getPersona();
        List<MedioDeContacto> mediosDeContactos = colaboradorDeSesion.getMediosDeContacto();
        List<MedioDeContactoDTO> mediosDeContactoDTO = mediosDeContactos.stream()
                .map(medio -> new MedioDeContactoDTO(medio.getTipo(), medio.getValor()))
                .toList();

        ColaboradorJuridicoDTO colaborador = new ColaboradorJuridicoDTO(
                usuarioDeSesion.getUsuario(),
                usuarioDeSesion.getContrasenia(),
                personaJuridica.getRazonSocial(),
                personaJuridica.getTipoDeOrganizacion(),
                personaJuridica.getRubro(),
                personaJuridica.getDireccion().getCalle(),
                personaJuridica.getDireccion().getAltura(),
                mediosDeContactoDTO);
        return colaborador;
    }

    public List<Sexo> sexos() {
        List<Sexo> sexos = new ArrayList<>();

        sexos.add(Sexo.FEMENINO);
        sexos.add(Sexo.MASCULINO);
        sexos.add(Sexo.OTRO);

        return sexos;
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

    public List<String> medios() {
        List<String> medios = new ArrayList<>();

        medios.add("WhatsApp");
        medios.add("Mail");

        return medios;
    }

    public List<TipoOrganizacion> tiposDeOrganizacion() {
        List<TipoOrganizacion> tiposOrganizacion = new ArrayList<>();

        tiposOrganizacion.add(TipoOrganizacion.GUBERNAMENTAL);
        tiposOrganizacion.add(TipoOrganizacion.EMPRESA);
        tiposOrganizacion.add(TipoOrganizacion.ONG);
        tiposOrganizacion.add(TipoOrganizacion.INSTITUCION);

        return tiposOrganizacion;
    }
    
    private ResponseCookie generarCookieParaLaModificacionDeUsuario(Usuario usuario) {
        String token = UtilsJWT.generarToken(usuario.getUsuario());
        ResponseCookie cookie = GeneradorDeCookie.generarCookie(token);

        return cookie;
    }

    private List<MedioDeContacto> mediosContacto(Colaborador colaborador) {
        Integer id = colaborador.getId_colaborador();
        return medioDeContactoRepository.traerMediosSegunId(id);
    }

    @PostMapping("/DefinirColaborador")
    public String solicitarTarjeta(@RequestParam("tipoDeColaborador") String tipoColaborador,
                                   RedirectAttributes redirectAttributes) {
        if(tipoColaborador.equals("colaboradorHumano")) {
            Documento nuevoDocumento = new Documento(null, null, null);
            Direccion nuevaDireccion = new Direccion(null, null);
            PersonaHumana nuevaPersona = new PersonaHumana(null, null, null, nuevoDocumento, nuevaDireccion);
            Colaborador colaboradorActual = gestorInicioDeSesion.obtenerColaboradorPorID();
            colaboradorActual.setPersona(nuevaPersona);
            colaboradorRepository.save(colaboradorActual);
        }
        else if(tipoColaborador.equals("colaboradorJuridico")) {
            Direccion nuevaDireccion = new Direccion(null, null);
            PersonaJuridica nuevaPersona = new PersonaJuridica(null, null, null, nuevaDireccion);
            Colaborador colaboradorActual = gestorInicioDeSesion.obtenerColaboradorPorID();
            colaboradorActual.setPersona(nuevaPersona);
            colaboradorRepository.save(colaboradorActual);
        }

        return "redirect:/ModificarColaborador";
    }
}