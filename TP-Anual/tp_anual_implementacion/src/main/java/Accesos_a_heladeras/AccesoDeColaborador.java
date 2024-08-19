package Accesos_a_heladeras;

import colaborador.Colaborador;
import heladera.Heladera;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static Accesos_a_heladeras.MotivoApertura.RETIRAR_VIANDA;

public class AccesoDeColaborador extends AccesoAHeladeras{
    private Colaborador colaborador;
    private List<SolicitudDeApertura> solicitudesDeApertura;

    public AccesoDeColaborador(List<Acceso> historicoDeAccesosHeladera,
                               String codigoTarjeta,
                               Colaborador colaborador,
                               List<SolicitudDeApertura> solicitudesDeApertura) {
        super(historicoDeAccesosHeladera, codigoTarjeta);
        this.colaborador = colaborador;
        this.solicitudesDeApertura = solicitudesDeApertura;
    }


    // -----------------------

    @Override
    public void registrarAcceso(Heladera heladera) {
        // Instancio un nuevo acceso a esa heladera
        // Lo agrego a la lista historicoAccesos...
        historicoDeAccesosHeladera.add(new Acceso(heladera,
                RETIRAR_VIANDA, // TODO: Revisar como le pasamos el motivo de apertura.
                LocalDateTime.now()));
    }
    private void iniciarTimer(SolicitudDeApertura solicitud){
        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                retirarSolicitud(solicitud);
                timer.cancel();  // Cancelar el timer después de que se ejecute la tarea
            }
        };
        timer.schedule(tarea, 10800000); // 3 horitas
    }

    @Override
    public Boolean autorizarApertura() {
        // TODO: Revisar si deberiamos pasarle la heladera para buscar una solicitud en esa heladera
        return null;
    }

    public void agregarSolicitud(SolicitudDeApertura solicitud){
        solicitudesDeApertura.add(solicitud);
        iniciarTimer(solicitud);
    }
    public void retirarSolicitud(SolicitudDeApertura solicitud){
        solicitudesDeApertura.remove(solicitud); // Elimina la primera aparicion del objeto.
    }

}
