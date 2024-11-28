package Modelo.Dominio.Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.AccesoAHeladeras;
import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccesoDeColaboradorRepository extends JpaRepository<AccesoDeColaborador, Integer> {}
