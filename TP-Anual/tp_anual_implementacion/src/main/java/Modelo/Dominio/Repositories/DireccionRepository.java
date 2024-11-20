package Modelo.Dominio.Repositories;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DireccionRepository extends JpaRepository<Direccion, Integer> {}
