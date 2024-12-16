package Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.AccesoAHeladeras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccesoAHeladerasRepository extends JpaRepository<AccesoAHeladeras, Integer> {

    @Query("SELECT v FROM AccesoAHeladeras v WHERE v.codigoTarjeta = :codigoTarjeta")
    Optional<AccesoAHeladeras> findByCodigoTarjeta(@Param("codigoTarjeta") String codigoTarjeta);

}
