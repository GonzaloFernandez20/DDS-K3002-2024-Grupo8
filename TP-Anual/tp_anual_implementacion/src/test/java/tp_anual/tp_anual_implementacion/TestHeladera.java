package tp_anual.tp_anual_implementacion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.localizacion.Direccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TestHeladera {
    private Vianda vianda1;
    private Vianda vianda2;
    private Vianda vianda3;
    private Heladera heladera1;
    private Heladera heladera2;
    private Colaborador colaboradorACargoViejo;
    private Colaborador colaboradorACargoNuevo;
    private Colaborador colaboradorDonanteViejo;
    private Colaborador colaboradorDonanteNuevo;
    private PersonaHumana personaDonanteVieja;
    private PersonaHumana personaDonanteNueva;
    private PersonaJuridica personaACargoVieja;
    private PersonaJuridica personaACargoNueva;
    private Documento documentoDonanteVieja;
    private Documento documentoDonanteNueva;
    private Direccion direccionVieja;
    private Direccion direccionNueva;
    private Modelo modeloEstandar;
    private List<Vianda> stock1 = new ArrayList<Vianda>();
    private List<Vianda> stock2 = new ArrayList<Vianda>();
    @BeforeEach
    void setUp(){
        modeloEstandar      = new Modelo(10F, 2F);
        personaACargoNueva  = new PersonaJuridica("SAS", TipoOrganizacion.empresa, "Automotor", null);
        personaACargoVieja  = new PersonaJuridica("SRL", TipoOrganizacion.ong, "Textil", null);
        personaDonanteVieja = new PersonaHumana("Carolina", "Castellucci", null, null, null);
        personaDonanteNueva = new PersonaHumana("Sofia", "Cachero", null, null, null);
        colaboradorACargoViejo  = new Colaborador(personaACargoVieja, null);
        colaboradorACargoNuevo  = new Colaborador(personaACargoNueva, null);
        colaboradorDonanteViejo = new Colaborador(personaDonanteVieja, null);
        colaboradorDonanteNuevo = new Colaborador(personaDonanteNueva, null);
        heladera1 = new Heladera(colaboradorACargoViejo,null , 3,modeloEstandar , null);
        heladera1.recibirViandas(stock1);
        heladera2 = new Heladera(colaboradorACargoNuevo, null , 2, modeloEstandar, null);
        heladera2.recibirViandas(stock2);
        vianda1 = new Vianda("Arroz",LocalDate.of(2025,12,2), colaboradorDonanteViejo,null,"1000","400");
        vianda1.setEstadoVianda(EstadoVianda.ENTREGADA);
        vianda2 = new Vianda("Pollo",LocalDate.of(2030,11,1), colaboradorDonanteNuevo,null,"2000","800");
        vianda2.setEstadoVianda(EstadoVianda.ENTREGADA);
        vianda3 = new Vianda("Milanesa",LocalDate.of(2030,12,2), colaboradorDonanteViejo,null,"1000","400");
        vianda3.setEstadoVianda(EstadoVianda.ENTREGADA);

/*        //NO TIENEN QUE SALTAR LAS EXCEPCIONES ACÄ
        try{vianda1.trasladar(heladera1);}catch (ViandaRechazada e){throw new RuntimeException(e);}
        try{vianda2.trasladar(heladera2);}catch (ViandaRechazada e){throw new RuntimeException(e);}
        try{vianda3.trasladar(heladera2);}catch (ViandaRechazada e){throw new RuntimeException(e);}*/
    }
/*    @Test
    void heladeraLlena(){
        Throwable exception = assertThrows(ViandaRechazada.class, () -> vianda1.trasladar(heladera2));
        assertEquals("La heladera está llena",exception.getMessage());
    }
    @Test
    void heladeraVacia(){
        Throwable exception = assertThrows(FallaHeladera.class, () -> heladera2.retirarViandas(100));
        assertEquals("Se quisieron retirar mas viandas que las que había en la heladera",exception.getMessage());
    }*/

}
