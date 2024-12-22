package Modelo.Dominio.contribucion;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "registro_de_recaudacion")
public class RegistroDeRecaudacion {
    private static RegistroDeRecaudacion instancia;
    @Column(name = "monto")
    private float fondoRecaudado = 0;

    private RegistroDeRecaudacion() {
    }

    public static RegistroDeRecaudacion getInstancia() {
        if (instancia == null) {
            instancia = new RegistroDeRecaudacion();
        }
        return instancia;
    }

    public void recibirDinero(float dinero) {
        fondoRecaudado += dinero;
    }
}
