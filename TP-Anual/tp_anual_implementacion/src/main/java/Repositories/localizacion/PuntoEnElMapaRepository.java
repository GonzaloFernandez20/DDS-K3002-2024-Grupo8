package Repositories.localizacion;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.PuntoEnElMapa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PuntoEnElMapaRepository extends JpaRepository<PuntoEnElMapa, Integer> {}
