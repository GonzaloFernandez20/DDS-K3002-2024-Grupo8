package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import Servicios_Externos_APIs.API.MailService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Mail")
public class Mail extends MedioDeContacto{
    @Column(name = "correo")
    private String correo;

    //Constructores ---------------------------------------------------
    public Mail() {}
    public Mail(String correo) {this.correo = correo;}

    //Metodos ---------------------------------------------------------
    @Override
    public void notificar(String mensaje) {
        String subject = "Notificacion Heladera";
        MailService.sendEmail(subject,mensaje,correo);
    }
    @Override
    public String getValor() { return this.correo; }
    public String getCorreo() { return this.correo; }
}
