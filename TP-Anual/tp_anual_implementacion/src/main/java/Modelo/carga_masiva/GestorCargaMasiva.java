package Modelo.carga_masiva;

import Modelo.Dominio.Persona.PersonaHumana;
import Repositories.UsuariosRepository;
import Repositories.carga_masiva.ColaboracionesCSVRepository;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Repositories.colaborador.ColaboradorRepository;
import Utils.GeneradorDeCadenas;
import Modelo.seguridad.SesionActiva.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GestorCargaMasiva {

    static ColaboradorRepository colaboradorRepository;
    static ColaboracionesCSVRepository colaboracionesCSVRepository;
    static UsuariosRepository usuariosRepository;

    @Autowired
    public GestorCargaMasiva(ColaboradorRepository colaboradorRepository, ColaboracionesCSVRepository colaboracionesCSVRepository, UsuariosRepository usuariosRepository) {
        GestorCargaMasiva.colaboradorRepository = colaboradorRepository;
        GestorCargaMasiva.colaboracionesCSVRepository = colaboracionesCSVRepository;
        GestorCargaMasiva.usuariosRepository = usuariosRepository;
    }

    public GestorCargaMasiva() {}

    public static void migrar(String path) {
        try {
            if(!path.endsWith(".csv")) {
                throw new RuntimeException("El archivo ingresado no es del tipo correcto.");
            }

            ColaboracionesCSV colaboracionesCSV = new ColaboracionesCSV(path);
            List<Colaborador> colaboradores = colaboracionesCSV.obtenerColaboradores();
            colaboradorRepository.saveAll(colaboradores);

            System.out.println("Voy a guardar este path: " + colaboracionesCSV.getArchivo());
            colaboracionesCSVRepository.save(colaboracionesCSV);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Colaborador obtenerColaboradorSegunPresencia(String nombre, String apellido, TipoDeDocumento tipoDeDocumento, String nroDocumento, String mail) {
        Colaborador colaborador = colaboradorRepository.buscarColaboradorHumano(nombre, apellido, tipoDeDocumento, nroDocumento);

        if(Objects.isNull(colaborador)) {
            List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
            Documento documento = new Documento(tipoDeDocumento, nroDocumento, null);
            PersonaHumana persona = new PersonaHumana(nombre, apellido, null,documento,new Direccion());
            colaborador = new Colaborador(persona, mediosDeContacto);

            agregarNuevoUsuario(colaborador);
        }

        if(colaborador.getMediosDeContacto().stream().filter(medio -> medio instanceof Mail).map(medio -> (Mail) medio).noneMatch(unMail -> unMail.equals(mail))) {
            Mail mailMedio = new Mail(mail);
            colaborador.agregarMedioDeContacto(mailMedio);
        }

        colaboradorRepository.save(colaborador);

        return colaborador;
    }

    private static void agregarNuevoUsuario(Colaborador colaborador) {
        PersonaHumana persona = (PersonaHumana) colaborador.getPersona();

        String nombreUsuario = persona.getNombre() + persona.getApellido() + persona.getDocumento().getNumero();
        String contrasenia = GeneradorDeCadenas.generarContraseniaSegura();

        System.out.println("Nombre usuario: " + nombreUsuario);
        System.out.println("Contraseña: " + contrasenia);

        Usuario usuario = new Usuario(nombreUsuario, contrasenia, colaborador);

        colaborador.notificar("¡Gracias por su aporte! Puede entrar al sistema con:" +
                "Nombre de usuario: " + nombreUsuario +
                "Contraseña: " + contrasenia +
                "Una vez que entre deberá corregir y completar sus datos como corresponda en la sección Mi Cuenta.");

        usuariosRepository.save(usuario);
    }
}
