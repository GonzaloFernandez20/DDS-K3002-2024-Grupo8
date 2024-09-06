package tp_anual.tp_anual_implementacion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.contribucion.EstadoVianda;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.localizacion.Direccion;
import nuestras_excepciones.ViandaRechazada;
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
public class TestsVianda {
    private Vianda vianda1;
    private Vianda vianda2;
    private Vianda viandaVencida;
    private Heladera heladeraVieja;
    private Heladera heladeraNueva;
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
    void setUp() {
        modeloEstandar      = new Modelo(10F, 2F);
        personaACargoNueva  = new PersonaJuridica("SAS", TipoOrganizacion.empresa, "Automotor", null);
        personaACargoVieja  = new PersonaJuridica("SRL", TipoOrganizacion.ong, "Textil", null);
        personaDonanteVieja = new PersonaHumana("Carolina", "Castellucci", null, null, null);
        personaDonanteNueva = new PersonaHumana("Sofia", "Cachero", null, null, null);
        colaboradorACargoViejo  = new Colaborador(personaACargoVieja);
        colaboradorACargoNuevo  = new Colaborador(personaACargoNueva);
        colaboradorDonanteViejo = new Colaborador(personaDonanteVieja);
        colaboradorDonanteNuevo = new Colaborador(personaDonanteNueva);
        heladeraVieja = new Heladera(colaboradorACargoViejo, modeloEstandar, stock1, null,3,null);
        heladeraNueva = new Heladera(colaboradorACargoNuevo, modeloEstandar, stock2, null,4,null);
        vianda1 = new Vianda("Arroz",LocalDate.of(2025,12,2), LocalDate.now(),colaboradorDonanteViejo,null,"1000","400", EstadoVianda.entregada);
        vianda2 = new Vianda("Pollo",LocalDate.of(2030,11,1), LocalDate.now(),colaboradorDonanteNuevo,null,"2000","800",EstadoVianda.entregada);
        viandaVencida = new Vianda("Milanesa",LocalDate.of(2023,12,2), LocalDate.now(),colaboradorDonanteViejo,null,"1000","400", EstadoVianda.entregada);
        stock1.add(vianda1);
        stock2.add(vianda2);
        try {
            heladeraNueva.recibirViandas(stock2);
        } catch (ViandaRechazada e) {
            throw new RuntimeException(e);
        }
        try {
            heladeraVieja.recibirViandas(stock1);
        } catch (ViandaRechazada e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void ValidacionDeTrasladoSimple(){
        try {
            vianda1.trasladar(heladeraNueva);
        } catch (ViandaRechazada e) {
            throw new RuntimeException(e);
        }
        assertEquals(heladeraNueva,vianda1.getHeladera(),"La vianda fue trasladada a una heladera nueva");

    }
    @Test
    void ValidacionDeVencimientos(){
        assertEquals(EstadoVianda.vencida,viandaVencida.getEstado(),"La vianda está vencida");
        assertEquals(EstadoVianda.entregada,vianda1.getEstado(),"La vianda no está vencida");
    }


}
