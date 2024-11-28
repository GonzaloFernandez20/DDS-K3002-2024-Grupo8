package Modelo.Dominio.Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.AccesoAHeladeras;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccesoAHeladerasRepository extends JpaRepository<AccesoAHeladeras, Integer> {}
