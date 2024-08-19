package Accesos_a_heladeras;

import contribucion.Contribucion;
import heladera.Heladera;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class SolicitudDeApertura {
    private Contribucion contribucion;
    private Heladera heladera;
    private MotivoApertura motivo;
    private LocalDateTime vencimiento;

    public SolicitudDeApertura(Contribucion contribucion, Heladera heladera, MotivoApertura motivo, LocalDateTime vencimiento) {
        this.contribucion = contribucion;
        this.heladera = heladera;
        this.motivo = motivo;
        this.vencimiento = vencimiento;
    }

    public void notificarQueSeAcaboElTiempo(){
        /*// Pasadas las 3 horas
        Timer timer = new Timer();

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                // Eliminar aquella solicitud de apertura del colaborador en una heladera
                timer.cancel();  // Cancelar el timer después de que se ejecute la tarea
            }
        };
        timer.schedule(tarea, 5000);*/
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
    public LocalDateTime getVencimiento() {
        return vencimiento;
    }
    public void setVencimiento(LocalDateTime vencimiento) {
        this.vencimiento = vencimiento;
    }
}
