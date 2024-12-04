package Repositories;

import Modelo.Dominio.Accesos_a_heladeras.Apertura;
import Modelo.Dominio.Accesos_a_heladeras.AperturaConPermiso;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AperturaConPermisoRepository extends JpaRepository<AperturaConPermiso, Integer> {}
