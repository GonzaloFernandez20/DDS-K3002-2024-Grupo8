package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.persona.Persona;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
@Entity
@Table(name = "AccesoDeColaborador")
public class AccesoDeColaborador extends AccesoAHeladeras{
    @OneToOne
    @JoinColumn(name = "colaborador", referencedColumnName = "id_colaborador")
    private Colaborador colaborador;

    public AccesoDeColaborador(String codigoTarjeta,
                               Colaborador colaborador) {
        this.codigoTarjeta = codigoTarjeta;
        this.colaborador = colaborador;
        this.historicoDeAccesosHeladera = new ArrayList<>();
    }
    // ----------------------------------------------------------
    @Override
    public boolean aperturaAutorizada(Heladera heladera) { // -> Buscar que haya hecha un permiso en esa heladera y que no este vencido.
        Optional<PermisoDeApertura> permiso = historicoDeAccesosHeladera.stream()
                                                                .filter(permisoDeApertura -> permisoDeApertura.esValida(heladera))
                                                                .findFirst();
        if (permiso.isPresent()){
            PermisoDeApertura permisoEncontrado = permiso.get();
            registrarAcceso((PermisoDeAperturaParaColaborar) permisoEncontrado);
            return true;
        } else return false;
    }

    private void registrarAcceso(PermisoDeAperturaParaColaborar permiso) {
        permiso.getContribucion().procesarLaContribucion();
        if(!permiso.getEstaVencida()) {
            historicoDeAccesosHeladera.add(permiso);
        }
    }


    public Persona getPersonaHumana() {
        return colaborador.getPersona();
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    // Hecho de forma provisoria para reportes
    public Integer cantidadDeAperturasPorDonacionesEntre(LocalDate fechaInicio,LocalDate fechaFin){
        return 10;//RE TRUCHO, CUANDO ESTË LISTA LA BD LO CORRIJO
        //return historicoDeAccesosHeladera.stream().filter(apertura -> apertura.aperturaParaEntregaDeDonacionEntre(fechaInicio, fechaFin)).toList().size();
    }
}