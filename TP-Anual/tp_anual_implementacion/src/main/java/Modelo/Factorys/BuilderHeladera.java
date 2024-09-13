package Modelo.Factorys;

import DTOs.HeladeraDTO;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;

public class BuilderHeladera {
    public static Heladera crearHeladeraAPartirDe(HeladeraDTO dto) {
        Modelo modelo = crearModelo(dto);
        Ubicacion ubicacion = crearUbicacion(dto);
        Heladera nuevaHeladera = new Heladera(
                dto.getColaboradorACargo(),
                ubicacion,
                dto.getCapacidadViandas(),
                modelo,
                dto.getPuestaEnFuncionamiento() );
        crearNotificadorDeSuscriptos(nuevaHeladera);
        return nuevaHeladera;
    }

    private static Modelo crearModelo(HeladeraDTO dto) {
        return new Modelo(dto.getTempMAXmodelo(), dto.getTempMINmodelo());
    }

    private static Ubicacion crearUbicacion(HeladeraDTO dto) {
        Direccion nuevaDireccion = new Direccion( dto.getCalle(), dto.getAltura(), dto.getCodPostal());
        return new Ubicacion(nuevaDireccion, dto.getCiudad(), dto.getNombreDelPunto());
    }

    private static void crearNotificadorDeSuscriptos(Heladera nuevaHeladera) {
        NotificadorDeSuscriptos notificador = new NotificadorDeSuscriptos(nuevaHeladera);
        nuevaHeladera.setNotificadorDeSuscriptos(notificador);
    }

    private static SensoreoDeMovimiento crearSensoreoDeMovimiento(Heladera nuevaHeladera) {
        return new SensoreoDeMovimiento(nuevaHeladera);
    }

    private static SensoreoDeTemperatura crearSensoreoDeTemperatura(Heladera nuevaHeladera) {
        return new SensoreoDeTemperatura(nuevaHeladera);
    }

}
