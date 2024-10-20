package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.*;

@Entity
@Table(name = "Mail")
public class Mail implements MedioDeContacto{
    @Id
    @GeneratedValue
    private  Integer id_mail;
    @Column(name = "correo")
    private String correo;

    public Mail(String correo) {
        if (correo == null) {throw new IllegalArgumentException("El correo no puede ser nulo");}
        this.correo = correo;
    }


    @Override
    public void notificar(String mensaje) {}
}