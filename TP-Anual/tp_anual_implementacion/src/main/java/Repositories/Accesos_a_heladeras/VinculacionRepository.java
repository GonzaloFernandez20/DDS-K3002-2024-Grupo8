package Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.Vinculacion;
import Modelo.Dominio.Persona_vulnerable.PersonaSituacionVulnerable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface VinculacionRepository extends JpaRepository<Vinculacion, Integer> {

    @Query("SELECT v FROM Vinculacion v WHERE v.codigoTarjeta = :codigoTarjeta")
    Optional<Vinculacion> findByCodigoTarjeta(@Param("codigoTarjeta") String codigoTarjeta);

}
