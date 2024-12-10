package Modelo.Dominio.Repositories.colaborador;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    @Query(value = "SELECT * FROM colaborador WHERE id_colaborador = ?1", nativeQuery = true)
    Colaborador obtenerColaboradorSegunID(String id_colaborador);

    @Query(
            value = "SELECT c " +
                    "FROM Colaborador c " +
                    "   JOIN PersonaHumana ph ON c.persona.id_persona = ph.id_persona " +
                    "WHERE ph.nombre = ?1 " +
                    "   AND ph.apellido = ?2 " +
                    "   AND ph.documento.tipo = ?3 " +
                    "   AND ph.documento.numero = ?4"
    )
    Colaborador buscarColaborador(String nombre, String apellido, TipoDeDocumento tipoDeDocumento, String documento);
}
