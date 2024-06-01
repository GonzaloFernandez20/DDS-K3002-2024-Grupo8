package TPAnual;

import java.util.List;

public class Colaborador {
    private List<MedioDeContacto> mediosDeContacto;
    private Direccion direccion;
    private List<Contribucion> contribucionesRealizadas;
    private Float puntos;

    private void sumarContribucion(Contribucion contribucion){
        contribucionesRealizadas.add(contribucion);
    }origin
    private void calcularPuntos() {}
    private void intercambiarPuntos() {}
}
    
class PersonaHumana extends Colaborador{
        String nombre;
        String apellido;
        Fecha fechaDeNacimiento;
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
