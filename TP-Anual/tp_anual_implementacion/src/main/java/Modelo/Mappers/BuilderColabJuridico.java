package Modelo.Mappers;

import DTOs.ColaboradorJuridicoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.Persona.PersonaJuridica;

public class BuilderColabJuridico {
    public static Colaborador crearColaboradorJuridicoAPartirDe(ColaboradorJuridicoDTO colaboradorDTO){
        Colaborador nuevoColaborador = new Colaborador(
                new PersonaJuridica(
                        colaboradorDTO.getRazonSocial(),
                        colaboradorDTO.getTipoDeOrganizacion(),
                        colaboradorDTO.getRubro(),
                        new Direccion(
                                colaboradorDTO.getCalle(),
                                colaboradorDTO.getAltura()
                        )),
                colaboradorDTO.getMediosDeContacto()
        );
        return nuevoColaborador;
    }
}
