package Modelo.Dominio.Repositories;

import Modelo.Dominio.Accesos_a_heladeras.AccesoAHeladeras;
import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccesoDeColaboradorRepository extends JpaRepository<AccesoDeColaborador, Integer> {}
