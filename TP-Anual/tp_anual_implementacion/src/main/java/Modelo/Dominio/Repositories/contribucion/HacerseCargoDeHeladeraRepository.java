package Modelo.Dominio.Repositories.contribucion;

import Modelo.Dominio.contribucion.HacerseCargoDeHeladera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HacerseCargoDeHeladeraRepository extends JpaRepository<HacerseCargoDeHeladera, Integer> {}
