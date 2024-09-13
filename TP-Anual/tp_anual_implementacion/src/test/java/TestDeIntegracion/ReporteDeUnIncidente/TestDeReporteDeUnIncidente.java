package TestDeIntegracion.ReporteDeUnIncidente;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.incidentes.GestorDeIncidentes;
import Modelo.Dominio.incidentes.Incidente;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.medios_de_contacto.Telegram;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import Modelo.Dominio.tecnico.LocalizadorDeTecnicos;
import Modelo.Dominio.tecnico.Tecnico;
import Repositorios.RepositorioIncidentes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.List;

import static Modelo.Dominio.heladera.EstadoHeladera.INACTIVA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/*  El objetivo de este test es probar el funcionamiento integral de todo lo referido
*   a reportar una falla, probando que cada uno de los componentes involucrados cumplan
*   con sus tareas respectivas
* */

@DisplayName("Prueba integral: Reporte de un incidente ")
public class TestDeReporteDeUnIncidente {
    FallaTecnica fallaTecnica;
    Heladera heladera;
    Colaborador colaboradorSuscriptor;
    List <Colaborador> colaboradoresSuscriptores;
    NotificadorDeSuscriptos notificadorDeSuscriptos;

    // <---------------------- Configuraciones ----------------------> //

    public void configuracionInicial(){
        inicializarHeladera();
        fallaTecnica = new FallaTecnica(null, "Vidrio Roto", heladera, "foto.png");
        suscribirColaboradores("se produjo una falla");
        reportarUnIncidente();
    }

    private void inicializarHeladera(){
        heladera = new Heladera(null,
                new Ubicacion( new Direccion("Medrano", "981",null),"CABA", "Heladera Medrano UTN"),
                5,
                null,
                null);
        notificadorDeSuscriptos = new NotificadorDeSuscriptos(heladera);
        heladera.setNotificadorDeSuscriptos(notificadorDeSuscriptos);
    }

    private void inicializarColaborador(){
        PersonaHumana persona = new PersonaHumana("Juan", "Pérez",null , null, null);
        List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
        mediosDeContacto.add(new Mail("juan.perez@gmail.com"));
        mediosDeContacto.add(new Telegram( "1132283796"));

        colaboradorSuscriptor = new Colaborador(persona, mediosDeContacto);
    }

    private void reportarUnIncidente(){
        GestorDeIncidentes.reportar(fallaTecnica);
    }

    private void suscribirColaboradores(String evento){
        colaboradoresSuscriptores = new ArrayList<>();

        inicializarColaborador();

        colaboradoresSuscriptores.add(colaboradorSuscriptor);
        colaboradoresSuscriptores.add(colaboradorSuscriptor);
        colaboradoresSuscriptores.add(colaboradorSuscriptor);
        colaboradoresSuscriptores.add(colaboradorSuscriptor);
        colaboradoresSuscriptores.add(colaboradorSuscriptor);

        for (Colaborador colaborador : colaboradoresSuscriptores){
            heladera.getNotificadorDeSuscriptos().suscribir(evento, colaborador);
        }
    }
    // -> El incidente fue reportado, toca testear todo lo que pasa despues

    // <---------------------- Testeos ----------------------> //

    @Nested
    @DisplayName("incidente reportado: efectos que se disparan hacia la Heladera")
    class TestHeladeraRecibeElReporte{

        @Test
        @DisplayName("El estado de la heladera ahora es \"INACTIVA\"")
        public void TestHeladeraQuedoInactiva(){
            configuracionInicial();
            assertEquals(INACTIVA, heladera.getEstado());
        }

        @Test
        @DisplayName("Todos los notificadores suscriptos a la heladera recibieron la notificacion sobre la falla")
        public void TestNotificadoresSuscriptos(){
            configuracionInicial();
            for (Colaborador colaborador : colaboradoresSuscriptores){
                assertTrue(colaborador.getMensajesRecibidos().contains("se produjo una falla"));
            }
        }
    }

    @Nested
    @DisplayName("incidente reportado: efectos que se disparan hacia el Tecnico")
    class Test2 {

        @Test
        @SuppressWarnings("")
        @DisplayName("El tecnico es notificado ante una falla en una heladera")
        public void testTecnicoNotificado() {
            // ------------ Configuracion del Mockito
            Tecnico tecnicoMock = mock(Tecnico.class);
            LocalizadorDeTecnicos localizadorMock = mock(LocalizadorDeTecnicos.class);
            when(localizadorMock.obtenerTecnicoMasCercano(any())).thenReturn(tecnicoMock);

            var mockedStatic = Mockito.mockStatic(LocalizadorDeTecnicos.class);
            mockedStatic.when(LocalizadorDeTecnicos::getInstancia).thenReturn(localizadorMock);
            // ------------ Flujo de ejecucion normal
            inicializarHeladera();
            fallaTecnica = new FallaTecnica(null, "Vidrio Roto", heladera, "foto.png");
            suscribirColaboradores("se produjo una falla");
            reportarUnIncidente();
            // ------------ Verificacion del mensaje
            String expectedMessage = "En la heladera: Heladera Medrano UTN (Medrano 981) " + "se produjo una Falla Tecnica: \n" + "Descripcion: " + "Vidrio Roto" + "\n" + "Link Foto: " + "foto.png";
            verify(tecnicoMock).notificar(expectedMessage);

        }
    }

    @Nested
    @DisplayName("incidente reportado: efecto disparado hacia el Repositorio de incidentes")
    class TestRepositorioDeIncidentes{
        @Test
        @DisplayName("El incidente quedo registrado en el registro de incidentes del sistema")
        public void testIncidenteQuedaRegistrado(){
            configuracionInicial();
            assertTrue(RepositorioIncidentes.getInstancia().getIncidentes().contains(fallaTecnica));
        }
    }



















}
