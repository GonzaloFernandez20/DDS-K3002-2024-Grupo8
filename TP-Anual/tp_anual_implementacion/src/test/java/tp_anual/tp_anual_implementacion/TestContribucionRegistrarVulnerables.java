package tp_anual.tp_anual_implementacion;
import colaborador.Colaborador;
import contribucion.RegistroDePersonasEnSituacionVulnerable;
import nuestras_excepciones.ColaboracionInvalida;
import localizacion.Direccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import persona.PersonaHumana;
import persona_vulnerable.AccesoAHeladeras;
import persona_vulnerable.EstadoDeVivienda;
import persona_vulnerable.PersonaSituacionVulnerable;
import persona_vulnerable.Vinculacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TestContribucionRegistrarVulnerables{
    private Direccion direccion;

    private PersonaHumana persona0;
    private PersonaHumana persona1;
    private PersonaHumana persona2;
    private PersonaHumana persona3;
    private PersonaHumana persona4;

    private PersonaSituacionVulnerable vulnerable0;
    private PersonaSituacionVulnerable vulnerable1;
    private PersonaSituacionVulnerable vulnerable2;
    private PersonaSituacionVulnerable vulnerable3;
    private PersonaSituacionVulnerable vulnerable4;

    private Vinculacion vinculacion0;
    private Vinculacion vinculacion1;
    private Vinculacion vinculacion2;
    private Vinculacion vinculacion3;
    private Vinculacion vinculacion4;

    private List<Vinculacion> vinculacionesEsperadas = new ArrayList<Vinculacion>();

    private List<PersonaSituacionVulnerable> vulnerablesARegistrar = new ArrayList<PersonaSituacionVulnerable>();

    PersonaHumana personaHumanaContribuyente0;
    PersonaHumana personaHumanaContribuyente1;

    Colaborador colaborador0;
    Colaborador colaborador1;

    RegistroDePersonasEnSituacionVulnerable contribucion0;
    RegistroDePersonasEnSituacionVulnerable contribucion1;

    private boolean vinculacionesDeIgualesAtributos(Vinculacion vinculacionA, Vinculacion vinculacionB ){
        return vinculacionA.getFechaRegistro().isEqual(vinculacionB.getFechaRegistro())
            && vinculacionA.getPersonaSituacionVulnerable().equals(vinculacionB.getPersonaSituacionVulnerable())
            && vinculacionA.getColaboradorQueRegistro().equals(vinculacionB.getColaboradorQueRegistro())
            && this.accesosAHeladerasDeIgualesAtributos(vinculacionA.getTarjetaEntregada(),vinculacionB.getTarjetaEntregada());
    }
    private boolean accesosAHeladerasDeIgualesAtributos(AccesoAHeladeras accesoAHeladerasA, AccesoAHeladeras accesoAHeladerasB){
        return (accesoAHeladerasA.getCantUsosPorDia() == accesoAHeladerasB.getCantUsosPorDia())
            && accesoAHeladerasA.getPersonaSituacionVulnerable().equals(accesoAHeladerasB.getPersonaSituacionVulnerable());
        //habría que comparar el id tmb, pero por cómo se generan en el sistema siempre serán distintos
    }

    @BeforeEach
    void setUp(){
        persona0 = new PersonaHumana("Carolina", "Castellucci", null, null, null);
        persona1 = new PersonaHumana("Valentin", "Pino", null, null, null);
        persona2 = new PersonaHumana("Sofia",    "Cachero", null, null, null);
        persona3 = new PersonaHumana("Abril",    "Vallecillo", null, null, null);
        persona4 = new PersonaHumana("Melina",   "Bugallo", null, null, null);

        vulnerable0 = new PersonaSituacionVulnerable(persona0, EstadoDeVivienda.SITUACION_DE_CALLE,  0);
        vulnerable1 = new PersonaSituacionVulnerable(persona1, EstadoDeVivienda.SITUACION_DE_CALLE,  1);
        vulnerable2 = new PersonaSituacionVulnerable(persona2, EstadoDeVivienda.SITUACION_DE_CALLE,  2);
        vulnerable3 = new PersonaSituacionVulnerable(persona3, EstadoDeVivienda.SITUACION_DE_CALLE,  3);
        vulnerable4 = new PersonaSituacionVulnerable(persona4, EstadoDeVivienda.SITUACION_DE_CALLE,  4);

        vulnerablesARegistrar.add(vulnerable0);
        vulnerablesARegistrar.add(vulnerable1);
        vulnerablesARegistrar.add(vulnerable2);
        vulnerablesARegistrar.add(vulnerable3);
        vulnerablesARegistrar.add(vulnerable4);

        direccion = new Direccion("St. Anas","666","80085","7");

        personaHumanaContribuyente0 = new PersonaHumana("Julius", "Caesar", null, null, direccion);
        personaHumanaContribuyente1 = new PersonaHumana("Servio", "Tulio", null, null, null);


        colaborador0 = new Colaborador(personaHumanaContribuyente0);
        colaborador1 = new Colaborador(personaHumanaContribuyente1);
        contribucion0 = new RegistroDePersonasEnSituacionVulnerable(colaborador0, LocalDate.of(2024,7,14), vulnerablesARegistrar);
        contribucion1 = new RegistroDePersonasEnSituacionVulnerable(colaborador1, LocalDate.of(2024,7,14), vulnerablesARegistrar);

        vinculacion0 = new Vinculacion(colaborador0,vulnerable0, LocalDate.now());
        vinculacion1 = new Vinculacion(colaborador0,vulnerable1, LocalDate.now());
        vinculacion2 = new Vinculacion(colaborador0,vulnerable2, LocalDate.now());
        vinculacion3 = new Vinculacion(colaborador0,vulnerable3, LocalDate.now());
        vinculacion4 = new Vinculacion(colaborador0,vulnerable4, LocalDate.now());

        vinculacionesEsperadas.add(vinculacion0);
        vinculacionesEsperadas.add(vinculacion1);
        vinculacionesEsperadas.add(vinculacion2);
        vinculacionesEsperadas.add(vinculacion3);
        vinculacionesEsperadas.add(vinculacion4);


    }
    @Test
    void registrarVulnerables(){
        try {//NO TIENE QUE SALTAR LA EXCEPCION
            contribucion0.procesarContribucion();
        } catch (ColaboracionInvalida e) {
            throw new RuntimeException(e);
        }

        boolean rtaAnterior = true;
        for(int i = 0; i< vinculacionesEsperadas.size();i++){
            Vinculacion vinculacionAux1 = vinculacionesEsperadas.get(i);
            rtaAnterior = rtaAnterior && contribucion0.getTarjetasRepartidas().stream().anyMatch(vinculacionAux2 -> this.vinculacionesDeIgualesAtributos(vinculacionAux1,vinculacionAux2));
        //No me deja usar un MatchAll con el MatchAny adentro así que tuve que armar el choclo este
        }
        assertTrue(rtaAnterior,"Las vinculaciones dan como esperamos que den");
    }
    @Test
    void elColaboradorNoTieneDireccion(){
        Throwable ex = assertThrows(ColaboracionInvalida.class,() -> contribucion1.procesarContribucion());
        assertEquals(ex.getMessage(),
                "El colaborador que registre a múltiples vulnerables debe tener DIRECCION");
    }
}
