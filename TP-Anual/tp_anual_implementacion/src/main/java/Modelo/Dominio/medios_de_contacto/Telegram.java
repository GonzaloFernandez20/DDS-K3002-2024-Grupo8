package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Telegram")
public class Telegram extends MedioDeContacto{
    @Column(name = "nro_de_telefono")
    private String nroDeTelefono;

    public Telegram(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    @Override
    public void notificar(String mensaje) {}
}
