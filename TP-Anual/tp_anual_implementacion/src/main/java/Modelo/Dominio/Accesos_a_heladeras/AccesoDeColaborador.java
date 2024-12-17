package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Setter
@Getter
@Entity
@Table(name = "acceso_de_colaborador")
public class AccesoDeColaborador extends AccesoAHeladeras{
    @OneToOne
    @JoinColumn(name = "colaborador"/*, referencedColumnName = "id_colaborador"*/)
    private Colaborador colaborador;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
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
            log.info("La tarjeta {} tiene autorizada la apertura de la {} ID:{}", getCodigoTarjeta(), heladera.getNombreDelPunto(), heladera.getid_heladera());
            AperturaConPermiso permisoEncontrado = permiso.get();
            registrarAperturaRealizada(permisoEncontrado);
            return true;
        } else{
            log.info("La tarjeta {} no tiene permiso de apertura de la {} ID:{}", getCodigoTarjeta(), heladera.getNombreDelPunto(), heladera.getid_heladera());
            return false;
        }
    }

    private void registrarAperturaRealizada(AperturaConPermiso apertura) {
        apertura.setFueConcretadaLaApertura(true);
        apertura.setFechaApertura(LocalDateTime.now());
        apertura.cantidadViandasInvolucradas = apertura.getContribucionAsociada().cantidadDeViandasInvolucradas();

        log.info("Se relizó una apertura de la {} ID:{} con la tarjeta {} con el motivo {}",
                apertura.getHeladera().getNombreDelPunto(), apertura.getHeladera().getid_heladera(), getCodigoTarjeta(), apertura.motivo.toString());
        apertura.getContribucionAsociada().procesarLaContribucion();
    }


}