package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.*;

@Entity
@Table(name = "Telegram")
public class Telegram implements MedioDeContacto{
    @Id
    @GeneratedValue
    private  Integer id_telegram;
    @Column(name = "nro_de_telefono")
    private String nroDeTelefono;

    public Telegram(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    @Override
    public void notificar(String mensaje) {}
}
