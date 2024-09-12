package Modelo.Dominio.sistema;

public class RegistroDeRecaudacion {
    private static RegistroDeRecaudacion instancia;
    private float fondoRecaudado;

    private RegistroDeRecaudacion() {
        fondoRecaudado = 0;
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
    public float getFondoRecaudado() { return fondoRecaudado; }

}
