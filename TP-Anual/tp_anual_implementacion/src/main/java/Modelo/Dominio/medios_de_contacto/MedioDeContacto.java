package Modelo.Dominio.medios_de_contacto;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MedioDeContacto {
    @Id
    @GeneratedValue
    private  Integer id_medio_de_contacto;
    public void notificar(String mensaje) {}
}
