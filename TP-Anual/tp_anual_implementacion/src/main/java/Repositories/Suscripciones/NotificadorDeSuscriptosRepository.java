package Repositories.Suscripciones;

import Modelo.Dominio.suscripcion.NotificadorDeSuscriptos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificadorDeSuscriptosRepository extends JpaRepository<NotificadorDeSuscriptos, Integer> {
}
