package Repositories.Accesos_a_heladeras;

import Modelo.Dominio.Accesos_a_heladeras.AccesoDeColaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccesoDeColaboradorRepository extends JpaRepository<AccesoDeColaborador, Integer> { }
