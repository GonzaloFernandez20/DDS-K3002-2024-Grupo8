package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccesoDeColaborador extends AccesoAHeladeras{
    private final Colaborador colaborador;
    private final List<PermisoDeApertura> permisosDeApertura;

    public AccesoDeColaborador(String codigoTarjeta,
                               Colaborador colaborador) {
        this.codigoTarjeta = codigoTarjeta;
        this.colaborador = colaborador;
        this.permisosDeApertura = new ArrayList<>();
        this.historicoDeAccesosHeladera = new ArrayList<>();
    }
    // ----------------------------------------------------------
    @Override
    public boolean aperturaAutorizada(Heladera heladera) { // -> Buscar que haya hecha un permiso en esa heladera y que no este vencido.
        Optional<PermisoDeApertura> permiso = permisosDeApertura.stream()
                                                                .filter(permisoDeApertura -> permisoDeApertura.esValida(heladera))
                                                                .findFirst();
        if (permiso.isPresent()){
            PermisoDeApertura permisoEncontrado = permiso.get();
            registrarAcceso(permisoEncontrado);
            return true;
        } else return false;
    }

    private void registrarAcceso(PermisoDeApertura permiso) {
        permiso.getContribucion().procesarLaContribucion();
        Apertura nuevaApertura = new Apertura(permiso.getHeladera(),
                                              permiso.getMotivo(),
                                              permiso.getContribucion().getViandas());
        historicoDeAccesosHeladera.add(nuevaApertura);
        retirarPermiso(permiso);
    }

    public void agregarPermiso(PermisoDeApertura permiso){
        permisosDeApertura.add(permiso);
    }
    public void retirarPermiso(PermisoDeApertura permiso){
        permisosDeApertura.remove(permiso); // Elimina la primera aparicion del objeto.
    }
}