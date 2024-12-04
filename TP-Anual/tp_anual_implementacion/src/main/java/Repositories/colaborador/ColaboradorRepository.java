package Repositories.colaborador;

import DAOs.ViandasPorColaboradorDAO;
import Modelo.Dominio.Usuario;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    @Query(value = "SELECT * FROM colaborador WHERE id_colaborador = ?1", nativeQuery = true)
    Colaborador obtenerColaboradorSegunID(String id_colaborador);

    @Query(value =
            "select new DAOs.ViandasPorColaboradorDAO(c.id_colaborador, cast(count(v.id_vianda) as int)) " +
            "from Colaborador c join Vianda v on c.id_colaborador = v.colaborador.id_colaborador " +
            "where v.fechaDeDonacion >= ?1 and v.fechaDeDonacion <= ?2 " +
            "group by c.id_colaborador")
    List<ViandasPorColaboradorDAO> traerViandasPorColaboradoresEntreFechas(LocalDate fecha_inicio, LocalDate fecha_fin);

}
