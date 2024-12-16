package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.Persona.Persona;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "acceso_de_colaborador")
public class AccesoDeColaborador extends AccesoAHeladeras{
    @OneToOne
    @JoinColumn(name = "colaborador"/*, referencedColumnName = "id_colaborador"*/)
    private Colaborador colaborador;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "acceso_a_heladeras", referencedColumnName = "codigo_tarjeta")
    private List <AperturaConPermiso> aperturasDeHeladera;

    //Constructores-------------------------------------------------------------
    public AccesoDeColaborador(String codigoTarjeta, Colaborador colaborador) {
        this.codigoTarjeta = codigoTarjeta;
        this.colaborador = colaborador;
        aperturasDeHeladera = new ArrayList<>();
    }

    public AccesoDeColaborador() {

    }

    //Metodos --------------------------------------------------------------------------------------------------------------------
    public void addPermisoDeApertura(AperturaConPermiso apertura) {
        this.aperturasDeHeladera.add(apertura);
    }

    @Override
    public boolean estaAutorizadaLaApertura(Heladera heladera) { // -> Buscar que haya un permiso para esa heladera y que no este vencido.
        Optional<AperturaConPermiso> permiso = aperturasDeHeladera.stream()
                                                                .filter(permisoDeApertura -> permisoDeApertura.esValidaEn(heladera))
                                                                .findFirst();
        if (permiso.isPresent()){
            AperturaConPermiso permisoEncontrado = permiso.get();
            registrarAperturaRealizada(permisoEncontrado);
            return true;
        } else return false;
    }

    private void registrarAperturaRealizada(AperturaConPermiso apertura) {
        apertura.setFueConcretadaLaApertura(true);
        apertura.setFechaApertura(LocalDateTime.now());
        apertura.cantidadViandasInvolucradas = apertura.getContribucion().cantidadDeViandasInvolucradas();
        apertura.getContribucion().procesarLaContribucion();

        //TODO: Registrar movimiento en BD
       // GestorDeAperturasAHeladeras.registrarAccesoDeColaborador(this);
    }


    //Getters y Setters ------------------------------------------------------------------------------------------------
    public Persona getPersonaHumana() {
        return colaborador.getPersona();
    }

    public Colaborador getColaborador() {return colaborador;}
    public void setColaborador(Colaborador colaborador) {this.colaborador = colaborador;}

    public List<AperturaConPermiso> getAperturasDeHeladera() {return aperturasDeHeladera;}
    public void setAperturasDeHeladera(List<AperturaConPermiso> aperturasDeHeladera) {this.aperturasDeHeladera = aperturasDeHeladera;}
}