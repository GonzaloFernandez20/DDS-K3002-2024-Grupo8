package colaborador;

import sistema.Sistema;

import java.util.List;

public class Permiso {
    private Sistema sistema;
    private List<RegistroAperturaHeladera> registros;
    private List<RegistroAperturaHeladera> solicitudes;

    public void registrar(RegistroAperturaHeladera registro) {
        sistema.registrarMovimiento(registro);
    }

    public void sumarRegistro(RegistroAperturaHeladera registro) {
        registros.add(registro);
    }

    public void sumarSolicitud(RegistroAperturaHeladera solicitud) {
        solicitudes.add(solicitud);
    }
}
