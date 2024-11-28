package Modelo.Dominio.Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.AccesoAHeladeras;
import Modelo.Dominio.Accesos_a_heladeras.PermisoDeAperturaParaColaborar;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermisoDeAperturaParaColaborarRepository extends JpaRepository<PermisoDeAperturaParaColaborar, Integer> {}
