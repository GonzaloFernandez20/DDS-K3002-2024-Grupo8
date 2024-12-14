package Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.AperturaConPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AperturaConPermisoRepository extends JpaRepository<AperturaConPermiso, Integer> {}
