package Repositories.medios_de_contacto;

import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MedioDeContactoRepository extends JpaRepository<MedioDeContacto, Integer> {

    @Query("SELECT c.mediosDeContacto FROM Colaborador c WHERE c.id_colaborador = id_colaborador")
    List<MedioDeContacto> traerMediosSegunId(@Param("id_colaborador") int id_colaborador);

}
