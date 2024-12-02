package Modelo.Mappers;

import DTOs.VinculacionPersonaVulnerableDTO;
import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Persona_vulnerable.EstadoDeVivienda;
import Modelo.Dominio.Persona_vulnerable.PersonaSituacionVulnerable;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;

public class VinculacionPersonaVulnerableMapper {
    public static Vinculacion crearVinculacionAPartirDeDTO(VinculacionPersonaVulnerableDTO dto, Colaborador colaborador){
        Vinculacion nuevaVinculacion = new Vinculacion(
                dto.getCodigoTarjeta(),
                colaborador,
                new PersonaSituacionVulnerable(
                        EstadoDeVivienda.valueOf(dto.getEstadoDeVivienda()),
                        dto.getCantidadMenores(),
                        new PersonaHumana(
                                dto.getNombre(),
                                dto.getApellido(),
                                dto.getFechaNacimiento(),
                                new Documento(
                                        TipoDeDocumento.valueOf(dto.getTipoDeDocumento()),
                                        dto.getNumeroDocumento(),
                                        Sexo.valueOf(dto.getSexo())
                                ),
                                new Direccion(
                                        dto.getDireccionCalle(),
                                        dto.getDireccionAltura()
                                )
                        )
                ));
        return nuevaVinculacion;
    }
}
