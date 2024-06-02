package TPAnual;

import java.util.Date;
import java.util.List;

public class Colaborador {
    private List<MedioDeContacto> mediosDeContacto;
    private Direccion direccion;
    private List<Contribucion> contribucionesRealizadas;
    private double puntos;
    private ReconocimientoPuntos reconocimientoPuntos;

    public void sumarContribucion(Contribucion contribucion){
        contribucionesRealizadas.add(contribucion);
    }
    private void calcularPuntos() {
        puntos = reconocimientoPuntos.contabilizar(contribucionesRealizadas);
    }
    private void intercambiarPuntos() {

    }
}
    
class PersonaHumana extends Colaborador{
        String nombre;
        String apellido;
        Date fechaDeNacimiento;
        Direccion direccion;
}

class PersonaJuridica extends Colaborador{
    String razonSocial;
    String tipoDeOrg;
    String rubro;
    Direccion direccion;

    void serNotificado() {
        // no hay suficiente informacion sobre como se comporta PersonaJuridica al
        // recibir la notificacion
    }
}

enum Tipo {
    gubernamental,
    ong,
    empresa,
    institucion
}
