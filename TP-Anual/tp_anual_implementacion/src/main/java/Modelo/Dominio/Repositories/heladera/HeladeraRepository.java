package Modelo.Dominio.Repositories.heladera;

import Modelo.Dominio.heladera.Heladera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeladeraRepository extends JpaRepository<Heladera, Integer> {}
