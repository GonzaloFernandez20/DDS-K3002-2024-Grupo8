package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.*;

@Entity
@Table(name = "WhatsApp")
public class WhatsApp implements MedioDeContacto{
    @Id
    @GeneratedValue
    private  Integer id_whatsapp;
    @Column(name = "nro_de_telefono")
    private String nroDeTelefono;

    public WhatsApp(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    @Override
    public void notificar(String mensaje) {}
}