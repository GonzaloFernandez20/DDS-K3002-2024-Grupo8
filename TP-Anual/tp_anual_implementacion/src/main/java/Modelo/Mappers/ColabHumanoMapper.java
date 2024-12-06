package Modelo.Mappers;

import DTOs.ColaboradorHumanoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.Telegram;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.seguridad.SesionActiva.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class ColabHumanoMapper {
    public static Usuario crearColaboradorHumanoAPartirDe(ColaboradorHumanoDTO colaboradorDTO){

        Usuario usuario = new Usuario(
                colaboradorDTO.getUsuario(),
                colaboradorDTO.getConstrasenia(),
                null);

        Colaborador nuevoColaborador = new Colaborador(
            new PersonaHumana(
                    colaboradorDTO.getNombre(),
                    colaboradorDTO.getApellido(),
                    colaboradorDTO.getFechaDeNacimiento(),
                    new Documento(
                            colaboradorDTO.getTipo(),
                            colaboradorDTO.getNumero(),
                            colaboradorDTO.getSexo()
                    ),
                    new Direccion(
                            colaboradorDTO.getCalle(),
                            colaboradorDTO.getAltura()
                    )
            ),
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
