package Modelo.Dominio.Repositories.medios_de_contacto;

import Modelo.Dominio.contribucion.Contribucion;
import Modelo.Dominio.medios_de_contacto.Mail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MailRepository extends JpaRepository<Mail, Integer> {}
