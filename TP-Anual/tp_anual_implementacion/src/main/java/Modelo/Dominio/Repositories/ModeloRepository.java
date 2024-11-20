package Modelo.Dominio.Repositories;

import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModeloRepository extends JpaRepository<Modelo, Integer> {}
