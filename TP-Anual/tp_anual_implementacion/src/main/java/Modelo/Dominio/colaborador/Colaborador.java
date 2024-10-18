package Modelo.Dominio.colaborador;

import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.persona.Persona;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Colaborador")
public class Colaborador {
    @Id
    @GeneratedValue
    private Integer id_colaborador;
    @OneToOne
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private final Persona persona;
    @OneToMany
    @JoinColumn(name = "id_medio_de_contacto", referencedColumnName = "id_medio_de_contacto")
    private final List<MedioDeContacto> mediosDeContacto;
    @ElementCollection
    private final List<String> mensajesRecibidos;
    @OneToMany
    @JoinColumn(name = "id_contribucion", referencedColumnName = "id_contribucion")
    private final List<Contribucion> historialDeContribuciones;
    @OneToOne(mappedBy = "id_colaborador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccesoDeColaborador tarjeta;
    @Column(name = "puntos_acumulados")
    private double puntosAcumulados;


    public Colaborador(Persona persona, List<MedioDeContacto> mediosDeContacto) {
        if(persona ==null){throw new IllegalArgumentException("El colaborador debe corresponderse a una persona");}
        if(mediosDeContacto == null || mediosDeContacto.isEmpty()){throw new IllegalArgumentException("Es necesario al menos un medio de contacto");}
        this.persona = persona;
        this.mediosDeContacto = mediosDeContacto;
        this.mensajesRecibidos = new ArrayList<>();
        this.historialDeContribuciones = new ArrayList<>();
        this.tarjeta = null;
        this.puntosAcumulados = 0;
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
    public Integer cantidadDeDonacionesDeViandaEntre(LocalDate fechaInicio, LocalDate fechaFin){
        if(tarjeta == null){return 0;}
        else{return tarjeta.cantidadDeAperturasPorDonacionesEntre(fechaInicio, fechaFin);}
    }


    // ---- Getters y Setters

    public void setTarjeta(AccesoDeColaborador tarjeta) {
        if(tarjeta == null){
            if(this.tarjeta != null) this.tarjeta.setColaborador(null);
        }
        else tarjeta.setColaborador(this);
        this.tarjeta = tarjeta;
    }

    public Direccion getDireccion() { return persona.getDireccion(); }
    public double getPuntosAcumulados() { return puntosAcumulados; }
    public List<Contribucion> getHistorialDeContribuciones() { return historialDeContribuciones; }
    public List<MedioDeContacto> getMediosDeContacto() { return mediosDeContacto; }
    public Persona getPersona() { return persona; }
    public AccesoDeColaborador getTarjeta() { return tarjeta; }
    public List<String> getMensajesRecibidos() { return mensajesRecibidos; }
}