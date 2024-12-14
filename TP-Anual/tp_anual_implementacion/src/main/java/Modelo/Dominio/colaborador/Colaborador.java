package Modelo.Dominio.colaborador;

import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.Persona.Persona;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "colaborador", referencedColumnName = "id_colaborador")
    private List<MedioDeContacto> mediosDeContacto;
    @ElementCollection
    private List<String> mensajesRecibidos;

    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name = "colaborador" ,referencedColumnName = "id_colaborador")
    private List<Contribucion> historialDeContribuciones;

    @OneToOne(mappedBy = "colaborador", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AccesoDeColaborador tarjeta;
    @Column(name = "puntos_acumulados")
    private double puntosAcumulados;


    public Colaborador(Persona persona, List<MedioDeContacto> mediosDeContacto) {
        this.persona = persona;
        this.mediosDeContacto = mediosDeContacto;
        this.mensajesRecibidos = new ArrayList<>();
        this.historialDeContribuciones = new ArrayList<>();
        this.tarjeta = null;
        this.puntosAcumulados = 0;
    }

    public Colaborador() {
        this.historialDeContribuciones = new ArrayList<>();
        this.mensajesRecibidos = new ArrayList<>();
    }

    public void registrarContribucion(Contribucion contribucion){
        historialDeContribuciones.add(contribucion);
        puntosAcumulados += contribucion.puntosQueSumaColaborador();
    }

    public void notificar(String mensaje) {
        mensajesRecibidos.add(mensaje);

        for (MedioDeContacto medio : mediosDeContacto){
            medio.notificar(mensaje);
        }
    }

    public void canjearPuntos(double puntosCanjeados) {
        puntosAcumulados -= puntosCanjeados;
    }

    public Boolean tieneMedioDeContacto(MedioDeContacto medioDeContacto) {
        return mediosDeContacto.stream().anyMatch(medio -> medio.equals(medioDeContacto));
    }

    public Boolean tieneDocumentoSegunNumeroYTipo(Documento documento) {
        //TODO: Revisar
/*      if(this.persona.getDocumento() == null){
            return false;
        } else {
            return this.persona.getDocumento().esDocumentoSegunNumeroYTipo(documento);
        }*/
        return true;
    }

    public void sacarMedioDeContacto(MedioDeContacto medioDeContacto) { mediosDeContacto.remove(medioDeContacto); }
    public void agregarMedioDeContacto(MedioDeContacto nuevoMedio) { mediosDeContacto.add(nuevoMedio);}

    // Hecho de forma provisoria para reportes
    public Integer cantidadDeDonacionesDeViandaEntre(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        if(tarjeta == null){return 0;}
        else{return tarjeta.cantidadDeAperturasPorDonacionesEntre(fechaInicio, fechaFin);}
    }
}