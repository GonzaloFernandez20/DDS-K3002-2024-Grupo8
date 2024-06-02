package TPAnual;

import java.util.List;

public class ReconocimientoPuntos {
    public double contabilizar(List<Contribucion> contribuciones) {
        double puntos = 0;
        for (int i = 0; i < contribuciones.size(); i++) {
            puntos += contribuciones.get(i).puntosQueSumaColaborador();
        }
        return puntos;
    }
}
