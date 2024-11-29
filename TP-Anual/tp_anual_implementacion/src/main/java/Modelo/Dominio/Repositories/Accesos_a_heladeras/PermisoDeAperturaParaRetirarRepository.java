package Modelo.Dominio.Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.PermisoDeAperturaParaColaborar;
import Modelo.Dominio.Accesos_a_heladeras.PermisoDeAperturaParaRetirar;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermisoDeAperturaParaRetirarRepository extends JpaRepository<PermisoDeAperturaParaRetirar, Integer> {}
