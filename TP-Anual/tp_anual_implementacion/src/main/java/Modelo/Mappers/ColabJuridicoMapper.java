package Modelo.Mappers;

import DTOs.ColaboradorJuridicoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Modelo.seguridad.GestorInicioDeSesion;
import Modelo.seguridad.SesionActiva.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class ColabJuridicoMapper {

    private static GestorInicioDeSesion gestorInicioDeSesion;

    @Autowired
    public ColabJuridicoMapper(GestorInicioDeSesion gestorInicioDeSesion) {
        this.gestorInicioDeSesion = gestorInicioDeSesion;
    }

    public static Usuario crearColaboradorJuridicoAPartirDe(ColaboradorJuridicoDTO colaboradorDTO){

        TipoOrganizacion tipoOrganizacion = colaboradorDTO.getTipoDeOrganizacion();

        Usuario usuario = new Usuario(
                colaboradorDTO.getUsuario(),
                colaboradorDTO.getConstrasenia(),
                null );

        Colaborador nuevoColaborador = new Colaborador(
                new PersonaJuridica(
                        colaboradorDTO.getRazonSocial(),
                        tipoOrganizacion,
                        colaboradorDTO.getRubro(),
                        new Direccion(
                                colaboradorDTO.getCalle(),
                                colaboradorDTO.getAltura()
                        )),
                new ArrayList<>()
        );

        // Creacion de medios de contacto
        colaboradorDTO.getMedioDeContactos().forEach(medio -> {
            if(medio.getTipo().equals("WhatsApp") && !Objects.equals(medio.getValor(), "")) {
                nuevoColaborador.agregarMedioDeContacto(new WhatsApp(medio.getValor()));
            }
            if(medio.getTipo().equals("Mail") && !Objects.equals(medio.getValor(), "")) {
                nuevoColaborador.agregarMedioDeContacto(new Mail(medio.getValor()));
            }
        });

        usuario.setColaborador(nuevoColaborador);
        return usuario;
    }

    public static Usuario actualizarDatosDeColaboradorJuridicoAPartirDe(ColaboradorJuridicoDTO colaboradorDTO){
        Usuario usuarioDeSesion = gestorInicioDeSesion.obtenerUsuarioDeSesion();
        Colaborador colaboradorDeSesion = usuarioDeSesion.getColaborador();

        usuarioDeSesion.setUsuario(colaboradorDTO.getUsuario());
        usuarioDeSesion.setContrasenia(colaboradorDTO.getConstrasenia());

        PersonaJuridica personaJuridica = (PersonaJuridica) colaboradorDeSesion.getPersona();

        personaJuridica.setTipoDeOrganizacion(colaboradorDTO.getTipoDeOrganizacion());
        personaJuridica.setRazonSocial(colaboradorDTO.getRazonSocial());
        personaJuridica.setRubro(colaboradorDTO.getRubro());
        personaJuridica.getDireccion().setCalle(colaboradorDTO.getCalle());
        personaJuridica.getDireccion().setAltura(colaboradorDTO.getAltura());

        //colaboradorDeSesion.setPersona(personaJuridica);
        //colaboradorDeSesion.setMediosDeContacto(colaboradorDTO.getMedioDeContactos());

        return usuarioDeSesion;
    }
}
