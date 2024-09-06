package Modelo.Dominio.reportes;

import java.time.LocalDateTime;

public abstract class ReporteSemanal {
    private LocalDateTime fecha;

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public abstract void reportar(int cantidadViandas);
}
