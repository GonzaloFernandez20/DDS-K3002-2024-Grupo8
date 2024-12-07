package Modelo.Dominio.Repositories.incidentes;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.incidentes.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlertaRepository extends JpaRepository<Alerta, Integer> {}
