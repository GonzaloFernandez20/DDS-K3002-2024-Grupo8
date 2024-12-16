package Modelo.Mappers;

import DTOs.HeladeraDTO;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.heladera.SensoreoDeMovimiento;
import Modelo.Dominio.heladera.SensoreoDeTemperatura;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
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

    public static Heladera actualizarHeladeraAPartirDe(Heladera heladera, HeladeraDTO dto) {
        heladera.getModelo().setNombreModelo(dto.getNombreModelo());
        heladera.getModelo().setTemperaturaMinima(dto.getTempMINmodelo());
        heladera.getModelo().setTemperaturaMaxima(dto.getTempMAXmodelo());
        heladera.getUbicacion().getDireccion().setAltura(dto.getAltura());
        heladera.getUbicacion().getDireccion().setCalle(dto.getCalle());
        heladera.getUbicacion().setCiudad(dto.getCiudad());
        heladera.setCapacidadDeViandas(dto.getCapacidadViandas());

        return heladera;
    }

    private static Modelo crearModelo(HeladeraDTO dto) {
        Modelo modelo = new Modelo(dto.getTempMAXmodelo(), dto.getTempMINmodelo());
        modelo.setNombreModelo(dto.getNombreModelo());
        return modelo;
    }

    private static Ubicacion crearUbicacion(HeladeraDTO dto) {
        Direccion nuevaDireccion = new Direccion( dto.getCalle(), dto.getAltura());
        PuntoEnElMapa punto = new PuntoEnElMapa(dto.getLatitud(), dto.getLongitud());
        return new Ubicacion(nuevaDireccion, dto.getCiudad(), dto.getNombreDelPunto(), punto);
    }

    private static void crearNotificadorDeSuscriptos(Heladera nuevaHeladera) {
        NotificadorDeSuscriptos notificador = new NotificadorDeSuscriptos(nuevaHeladera);
        nuevaHeladera.setNotificadorDeSuscriptos(notificador);
    }
}