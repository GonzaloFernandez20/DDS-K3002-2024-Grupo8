package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public interface MedioDeContacto {
    public void notificar(String mensaje);
}