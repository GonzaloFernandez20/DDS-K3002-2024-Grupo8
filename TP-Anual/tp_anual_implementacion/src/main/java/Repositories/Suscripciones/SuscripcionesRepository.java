package Repositories.Suscripciones;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.suscripcion.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuscripcionesRepository extends JpaRepository<Suscripcion, Integer> {

    @Query("SELECT s FROM Suscripcion s WHERE s.suscripto = :colaborador")
    List<Suscripcion> findByColaborador(@Param("colaborador") Colaborador colaborador);
}
