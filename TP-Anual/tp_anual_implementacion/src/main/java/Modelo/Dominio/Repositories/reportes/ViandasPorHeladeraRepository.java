package Modelo.Dominio.Repositories.reportes;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.reportes.ViandasPorColaborador;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ViandasPorHeladeraRepository extends JpaRepository<ViandasPorColaborador, Integer> {}
