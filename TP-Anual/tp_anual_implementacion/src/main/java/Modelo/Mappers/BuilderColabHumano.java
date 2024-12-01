package Modelo.Mappers;

import DTOs.ColaboradorHumanoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.Persona.PersonaHumana;

public class BuilderColabHumano {
    public static Colaborador crearColaboradorHumanoAPartirDe(ColaboradorHumanoDTO colaboradorDTO){
    Colaborador colaborador = new Colaborador(
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
                            colaboradorDTO.getAltura(),
                            colaboradorDTO.getCodPostal()
                    )
            ),
            colaboradorDTO.getMediosDeContacto()
    );
    return colaborador;
    }
}
