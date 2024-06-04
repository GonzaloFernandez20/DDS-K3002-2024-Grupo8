package colaborador;

import heladera.Direccion;

public class PersonaJuridica extends Colaborador{
    String razonSocial;
    TipoOrganizacion tipoDeOrganizacion;
    String rubro;

    public PersonaJuridica(String razonSocial, TipoOrganizacion tipoDeOrganizacion, String rubro, Direccion direccion) {
        super(direccion);
        this.razonSocial = razonSocial;
        this.tipoDeOrganizacion = tipoDeOrganizacion;
        this.rubro = rubro;
    }

    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public void setTipoDeOrganizacion(TipoOrganizacion tipoDeOrganizacion) { this.tipoDeOrganizacion = tipoDeOrganizacion; }

    public void setRubro(String rubro) { this.rubro = rubro; }

    public void serNotificado() {
        // no hay suficiente informacion sobre como se comporta PersonaJuridica al
        // recibir la notificacion
    }
}

enum TipoOrganizacion {
    gubernamental,
    ong,
    empresa,
    institucion
}