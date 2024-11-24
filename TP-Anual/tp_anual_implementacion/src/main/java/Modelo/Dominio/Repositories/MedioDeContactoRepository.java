package Modelo.Dominio.Repositories;

import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MedioDeContactoRepository extends JpaRepository<MedioDeContacto, Integer> {}
