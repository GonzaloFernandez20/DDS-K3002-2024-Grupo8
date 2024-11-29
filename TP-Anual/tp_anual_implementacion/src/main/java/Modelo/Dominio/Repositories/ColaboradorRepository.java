package Modelo.Dominio.Repositories;

import Modelo.Dominio.Usuario;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    @Query(value = "SELECT * FROM colaborador WHERE id_colaborador = ?1", nativeQuery = true)
    Colaborador obtenerColaboradorSegunID(String id_colaborador);

}
