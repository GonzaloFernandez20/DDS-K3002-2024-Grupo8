package Modelo.Dominio.Repositories.medios_de_contacto;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WhatsappRepository extends JpaRepository<WhatsApp, Integer> {}
