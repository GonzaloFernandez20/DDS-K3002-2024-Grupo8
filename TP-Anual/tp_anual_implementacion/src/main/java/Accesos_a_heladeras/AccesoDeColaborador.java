package Accesos_a_heladeras;

import colaborador.Colaborador;
import heladera.Heladera;
import nuestras_excepciones.ColaboracionInvalida;
import sistema.GestorDeContribuciones;

import java.util.List;
import java.util.Optional;

public class AccesoDeColaborador extends AccesoAHeladeras{
    private final Colaborador colaborador;
    private List<SolicitudDeApertura> solicitudesDeApertura;

    public AccesoDeColaborador(List<Acceso> historicoDeAccesosHeladera,
                               String codigoTarjeta,
                               Colaborador colaborador,
                               List<SolicitudDeApertura> solicitudesDeApertura) {
        super(historicoDeAccesosHeladera, codigoTarjeta);
        this.colaborador = colaborador;
        this.solicitudesDeApertura = solicitudesDeApertura;
    }
    // ----------------------------------------------------------

    private void registrarAcceso(SolicitudDeApertura solicitud) {
        Acceso nuevoAcceso = new Acceso(solicitud.getHeladera(),
                                        solicitud.getMotivo());
        historicoDeAccesosHeladera.add(nuevoAcceso);
        retirarSolicitud(solicitud);
    }

    @Override
    public Boolean aperturaAutorizada(Heladera heladera) { // -> Buscar que haya hecha una solicitud y no este vencida
        Optional<SolicitudDeApertura> solicitud = solicitudesDeApertura.stream()
                                                  .filter(solicitudDeApertura -> solicitudDeApertura.esValida(heladera))
                                                  .findFirst();
        if (solicitud.isPresent()){
            SolicitudDeApertura solicitudEncontrada = solicitud.get();
            registrarAcceso(solicitudEncontrada);
            solicitudEncontrada.cancelarTimer();
            // -> Se efectua la donacion...
            GestorDeContribuciones.realizarContribucion(
                    solicitudEncontrada.getContribucion(),
                    this.colaborador);

            return true;
        } else return false;
    }

    public void agregarSolicitud(SolicitudDeApertura solicitud){
        solicitudesDeApertura.add(solicitud);
        solicitud.iniciarTimer();
    }
    public void retirarSolicitud(SolicitudDeApertura solicitud){
        solicitudesDeApertura.remove(solicitud); // Elimina la primera aparicion del objeto.
    }
}