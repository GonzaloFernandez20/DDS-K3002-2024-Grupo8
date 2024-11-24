package Modelo.Dominio.Repositories;

import Modelo.Dominio.Accesos_a_heladeras.PermisoDeApertura;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermisoDeAperturaRepository extends JpaRepository<PermisoDeApertura, Integer> {}
