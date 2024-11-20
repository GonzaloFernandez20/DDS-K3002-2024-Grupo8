package Modelo.Dominio.Repositories;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContribucionRepository extends JpaRepository<Contribucion, Integer> {}
