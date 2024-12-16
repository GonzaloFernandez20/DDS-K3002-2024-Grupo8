package Repositories.heladera;

import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ModeloRepository extends JpaRepository<Modelo, Integer> {}
