package Modelo.Dominio.Repositories.colaborador;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.localizacion.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {}
