package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AccesoAHeladeras {
    @Id
    @GeneratedValue
    private Integer id_acceso_a_heladera;
    @Column(name = "codigo_tarjeta")
    protected String codigoTarjeta;
    @OneToMany
    @JoinColumn(name = "id_acceso_a_heladeras", referencedColumnName = "id_acceso_a_heladeras")
    protected List <PermisoDeApertura> historicoDeAccesosHeladera = null;

    public abstract boolean aperturaAutorizada(Heladera heladera);
    public String getCodigoTarjeta(){ return codigoTarjeta; }

    public void setCodigoTarjeta(String codigoTarjeta) {
        this.codigoTarjeta = codigoTarjeta;
    }
    public void addPermisoDeApertura(PermisoDeApertura permisoDeApertura) {
            this.historicoDeAccesosHeladera.add(permisoDeApertura);
    }
    public void removePermisoDeApertura(PermisoDeApertura permisoDeApertura) {
            this.historicoDeAccesosHeladera.remove(permisoDeApertura);
    }


}