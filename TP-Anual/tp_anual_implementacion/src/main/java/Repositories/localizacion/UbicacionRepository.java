package Repositories.localizacion;

import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UbicacionRepository extends JpaRepository<Ubicacion, Integer> {}
