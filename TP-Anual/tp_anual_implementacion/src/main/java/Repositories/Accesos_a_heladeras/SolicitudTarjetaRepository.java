package Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.SolicitudTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudTarjetaRepository extends JpaRepository<SolicitudTarjeta, Integer> {}
