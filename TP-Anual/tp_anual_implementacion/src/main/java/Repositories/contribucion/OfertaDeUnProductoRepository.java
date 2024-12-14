package Repositories.contribucion;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OfertaDeUnProductoRepository extends JpaRepository<OfertaDeUnProducto, Integer> {}
