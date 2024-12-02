package Modelo.Mappers;

import DTOs.ColaboradorJuridicoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.Telegram;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Modelo.seguridad.SesionActiva.Usuario;

import java.util.ArrayList;

public class ColabJuridicoMapper {
    public static Usuario crearColaboradorJuridicoAPartirDe(ColaboradorJuridicoDTO colaboradorDTO){

        TipoOrganizacion tipoOrganizacion = TipoOrganizacion.valueOf(colaboradorDTO.getTipoDeOrganizacion().toUpperCase());

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
        if (colaboradorDTO.isTieneWp()){
            nuevoColaborador.agregarMedioDeContacto(new WhatsApp(colaboradorDTO.getTelefono()));
        }
        if (colaboradorDTO.isTieneTg()){
            nuevoColaborador.agregarMedioDeContacto(new Telegram(colaboradorDTO.getTelefono()));
        }

        nuevoColaborador.agregarMedioDeContacto(new Mail(colaboradorDTO.getEmail()));


        usuario.setColaborador(nuevoColaborador);
        return usuario;
    }
}
