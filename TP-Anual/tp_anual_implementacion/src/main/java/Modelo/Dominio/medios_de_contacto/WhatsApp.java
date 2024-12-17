package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import Servicios_Externos_APIs.API.WhatsAppService;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "WhatsApp")
public class WhatsApp extends MedioDeContacto{
    @Column(name = "nro_de_telefono")
    private String nroDeTelefono;

    //Constructores --------------------------------------------------------------
    public WhatsApp() {}
    public WhatsApp(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    //Metodos --------------------------------------------------------------------
    @Override
    public void notificar(String mensaje) {
        WhatsAppService.sendTextMessage(nroDeTelefono, mensaje);
    }
    @Override
    public String getValor() { return this.nroDeTelefono; }
}
