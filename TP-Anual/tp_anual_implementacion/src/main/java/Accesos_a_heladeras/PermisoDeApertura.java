package Accesos_a_heladeras;

import contribucion.Contribucion;
import heladera.Heladera;

import java.util.Timer;
import java.util.TimerTask;

public class PermisoDeApertura {
    private Contribucion contribucion;
    private Heladera heladera;
    private MotivoApertura motivo;
    private Boolean vencida;
    private final Timer timer;

    public PermisoDeApertura(Contribucion contribucion, Heladera heladera, MotivoApertura motivo) {
        this.contribucion = contribucion;
        this.heladera = heladera;
        this.motivo = motivo;
        this.vencida = false;
        timer = new Timer();
        iniciarTimer();
    }

    public boolean esValida(Heladera heladera){
        return this.heladera.equals(heladera) && !vencida;
    }

    public void iniciarTimer(){

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                vencida = true;
                timer.cancel();  // Cancelar el timer después de que se ejecute la tarea
                // notificar q se acabo el tiempo
            }
        };
        timer.schedule(tarea, 10800000); // 3 horitas
    }

    public void cancelarTimer(){
        this.timer.cancel();
    }

    // ----------> Getters y Setters
    public Contribucion getContribucion() {
        return contribucion;
    }
    public void setContribucion(Contribucion contribucion) {
        this.contribucion = contribucion;
    }
    public Heladera getHeladera() {
        return heladera;
    }
    public void setHeladera(Heladera heladera) {
        this.heladera = heladera;
    }
    public MotivoApertura getMotivo() {
        return motivo;
    }
    public void setMotivo(MotivoApertura motivo) {
        this.motivo = motivo;
    }

}
