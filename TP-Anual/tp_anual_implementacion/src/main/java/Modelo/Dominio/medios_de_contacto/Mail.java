package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import Servicios_Externos_APIs.API.MailService;

@Entity
@Table(name = "Mail")
public class Mail extends MedioDeContacto{
    @Column(name = "correo")
    private String correo;

    public Mail() {}

    public Mail(String correo) {
        if (correo == null) {throw new IllegalArgumentException("El correo no puede ser nulo");}
        this.correo = correo;
    }

    @Override
    public void notificar(String mensaje) {
        String subject = "Notificacion Heladera";
        String correo = this.correo;
        MailService.sendEmail(subject,mensaje,correo);
    }
}
