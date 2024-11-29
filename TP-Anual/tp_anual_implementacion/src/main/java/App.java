import Modelo.Dominio.Repositories.colaborador.ColaboradorRepository;
import Modelo.Dominio.Repositories.contribucion.DonacionDeDineroRepository;
import Modelo.Dominio.Repositories.contribucion.HacerseCargoDeHeladeraRepository;
import Modelo.Dominio.Repositories.documentacion.DocumentoRepository;
import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
import Modelo.Dominio.Repositories.heladera.ModeloRepository;
import Modelo.Dominio.Repositories.localizacion.DireccionRepository;
import Modelo.Dominio.Repositories.localizacion.PuntoEnElMapaRepository;
import Modelo.Dominio.Repositories.localizacion.UbicacionRepository;
import Modelo.Dominio.Repositories.medios_de_contacto.MedioDeContactoRepository;
import Modelo.Dominio.Repositories.persona.PersonaJuridicaRepository;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.localizacion.Ubicacion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}