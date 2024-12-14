package Repositories.contribucion;

import Modelo.Dominio.contribucion.DonacionDeViandas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DonacionDeViandasRepository extends JpaRepository<DonacionDeViandas, Integer> {}
