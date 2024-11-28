package Modelo.Dominio.Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.PermisoDeAperturaParaColaborar;
import Modelo.Dominio.Accesos_a_heladeras.SolicitudTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SolicitudTarjetaRepository extends JpaRepository<SolicitudTarjeta, Integer> {}
