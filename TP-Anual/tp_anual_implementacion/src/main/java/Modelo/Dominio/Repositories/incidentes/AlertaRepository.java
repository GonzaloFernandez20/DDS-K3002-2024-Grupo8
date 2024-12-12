package Modelo.Dominio.Repositories.incidentes;

import Modelo.Dominio.incidentes.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
    @Query(
            value = "select a " +
                    "from Alerta a " +
                    "where a.heladeraDondeOcurrio.colaboradorACargo.id_colaborador = ?1"
    )
    List<Alerta> traerAlertasDeUnColaborador(Integer id_colaborador);
}
