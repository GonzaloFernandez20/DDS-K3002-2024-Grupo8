package Repositories.contribucion;

import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ViandaRepository extends JpaRepository<Vianda, Integer> {}
