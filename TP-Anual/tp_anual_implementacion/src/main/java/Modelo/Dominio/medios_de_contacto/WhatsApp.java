package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import Servicios_Externos_APIs.API.WhatsAppService;

@Entity
@Table(name = "WhatsApp")
public class WhatsApp extends MedioDeContacto{
    @Column(name = "nro_de_telefono")
    private String nroDeTelefono;

    public WhatsApp() {}
    public WhatsApp(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    @Override
    public void notificar(String mensaje) {
        String numero = this.nroDeTelefono;
        WhatsAppService.sendTextMessage(numero, mensaje);
    }
    @Override
    public String getValor() { return this.nroDeTelefono; }
}
