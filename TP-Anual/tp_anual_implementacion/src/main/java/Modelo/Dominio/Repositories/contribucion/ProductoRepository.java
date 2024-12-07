package Modelo.Dominio.Repositories.contribucion;

import Modelo.Dominio.contribucion.Producto;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepository extends JpaRepository<Producto, Integer> {}
