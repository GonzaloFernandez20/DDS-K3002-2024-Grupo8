package Repositories.contribucion;

import Modelo.Dominio.contribucion.DonacionDeDinero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DonacionDeDineroRepository extends JpaRepository<DonacionDeDinero, Integer> {}
