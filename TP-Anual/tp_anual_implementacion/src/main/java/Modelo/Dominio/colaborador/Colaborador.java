package Modelo.Dominio.colaborador;

import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.Persona.Persona;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Colaborador")
public class Colaborador {
    @Id
    @GeneratedValue
    private Integer id_colaborador;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "persona" ,referencedColumnName = "id_persona")
    private Persona persona;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador", referencedColumnName = "id_colaborador")
    private List<MedioDeContacto> mediosDeContacto;
    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contribucion> historialDeContribuciones;
    @OneToOne(mappedBy = "colaborador", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AccesoDeColaborador tarjeta;
    @Column(name = "puntos_acumulados")
    private double puntosAcumulados;

    //Constructores -------------------------------------------------------------------------------------------------
    public Colaborador(Persona persona, List<MedioDeContacto> mediosDeContacto) {
        this.persona = persona;
        this.mediosDeContacto = mediosDeContacto;
        this.historialDeContribuciones = new ArrayList<>();
        this.tarjeta = null;
        this.puntosAcumulados = 0;
    }

    public Colaborador() {
        this.historialDeContribuciones = new ArrayList<>();
    }

    // Metodos -------------------------------------------------------------------------------------------------------
    public void registrarContribucion(Contribucion contribucion){
        historialDeContribuciones.add(contribucion);
        double nuevosPuntos = contribucion.puntosQueSumaColaborador();
        puntosAcumulados += nuevosPuntos;
        logger.debug("Se sumaron {} puntos por la nueva cotribución, el colaborador queda con {} puntos acumulados", nuevosPuntos, puntosAcumulados);
    }

    public void notificar(String mensaje) {
        for (MedioDeContacto medio : mediosDeContacto){
            try {
                medio.notificar(mensaje);
                logger.info("Se notificó al colaborador a través de {}: {}", medio.getClass().getSimpleName(), mensaje);
            } catch (Exception e) {
                logger.error("Error al notificar a través de {}: {}", medio.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    public void canjearPuntos(double puntosCanjeados) {
        puntosAcumulados -= puntosCanjeados;
    }

    public void sacarMedioDeContacto(MedioDeContacto medioDeContacto) {
        mediosDeContacto.remove(medioDeContacto);
    }

    public void agregarMedioDeContacto(MedioDeContacto nuevoMedio) {
        mediosDeContacto.add(nuevoMedio);
    }

    //Logger ----------------------------------------------------------------------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(Colaborador.class);
}
