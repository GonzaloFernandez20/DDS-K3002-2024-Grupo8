package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ParaRetirar")
public class PermisoDeAperturaParaRetirar extends PermisoDeApertura{
    public PermisoDeAperturaParaRetirar(Heladera heladera, MotivoApertura motivo) {
        super(heladera, motivo);
    }

    @Override
    public boolean esValida(Heladera heladera) {
        return true;
        //El permiso para el vulnerable se genera al abrir la heladera, no de antemano como para el colaborador
    }
}
