package Modelo.carga_masiva;

import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.Repositories.carga_masiva.ColaboracionesCSVRepository;
import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.medios_de_contacto.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GestorCargaMasiva {

    static ColaboradorRepository colaboradorRepository;
    static ColaboracionesCSVRepository colaboracionesCSVRepository;

    @Autowired
    public GestorCargaMasiva(ColaboradorRepository colaboradorRepository, ColaboracionesCSVRepository colaboracionesCSVRepository) {
        GestorCargaMasiva.colaboradorRepository = colaboradorRepository;
        GestorCargaMasiva.colaboracionesCSVRepository = colaboracionesCSVRepository;
    }

    public GestorCargaMasiva() {}

    public static void migrar(String path) {
        try {
            if(!path.endsWith(".csv")) {
                throw new RuntimeException("El archivo ingresado no es del tipo correcto.");
            }

            ColaboracionesCSV colaboracionesCSV = new ColaboracionesCSV(path);
            List<Colaborador> colaboradores = colaboracionesCSV.obtenerColaboradores();
            colaboradores.stream().forEach(colaborador -> persistirSegunPresencia(colaborador));

            colaboracionesCSVRepository.save(colaboracionesCSV);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void persistirSegunPresencia(Colaborador colaborador) {
        PersonaHumana persona = (PersonaHumana) colaborador.getPersona();
        Documento documento = persona.getDocumento();
        Colaborador colaboradorPersistido = colaboradorRepository.buscarColaborador(persona.getNombre(), persona.getApellido(), documento.getTipo(), documento.getNumero());

        if (!Objects.isNull(colaboradorPersistido)) { // Si está en la BD...
            actualizarColaborador(colaboradorPersistido, colaborador);
        } else { // Si no está en la BD...
            colaborador.notificar("¡Gracias por su aporte! Puede entrar al sistema con su mail.");
            // Al no existir el colaborador previo a la carga, SÍ O SÍ va a ser un mail, cumpliendo con la consigna
            colaboradorRepository.save(colaborador);
        }
    }

    private static void actualizarColaborador(Colaborador colaboradorActualmente, Colaborador colaboradorDelCSV) {
        colaboradorDelCSV.getHistorialDeContribuciones().forEach(colaboradorActualmente::registrarContribucion);
        Mail mailDelCSV = (Mail) colaboradorDelCSV.getMediosDeContacto().getFirst();
        if(!colaboradorActualmente.tieneMedioDeContacto(mailDelCSV)) {
            colaboradorActualmente.agregarMedioDeContacto(mailDelCSV);
        }
        colaboradorRepository.save(colaboradorActualmente);
    }
}
