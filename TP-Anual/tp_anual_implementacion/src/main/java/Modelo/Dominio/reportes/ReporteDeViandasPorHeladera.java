package reportes;

import Accesos_a_heladeras.Aperturas;
import heladera.Heladera;
import sistema.RegistroDeHeladeras;
import sistema.Sistema;

import java.time.LocalDate;
import java.util.List;

public class ReporteDeViandasPorHeladera extends ReporteSemanal{
    private List<ViandasPorHeladera> viandasPorHeladeras = null;

    public ReporteDeViandasPorHeladera(LocalDate fechaDeCreacion) {
        super(fechaDeCreacion);
    }
   @Override
    public void completarReporte(){
        List<Heladera> heladerasConocidas = RegistroDeHeladeras.getInstancia().getHeladeras();
        heladerasConocidas.forEach(heladera -> {
            ViandasPorHeladera viandasPorHeladera =
                    new ViandasPorHeladera(heladera,
                            Aperturas.getInstancia().cantidadDeDepositosDeHeladeraEntreFechas(heladera,LocalDate.now().minusWeeks(1),LocalDate.now()),
                            Aperturas.getInstancia().cantidadDeRetirosDeHeladeraEntreFechas(heladera,LocalDate.now().minusWeeks(1),LocalDate.now()));
            this.sumarViandasPorHeladera(viandasPorHeladera);
        });
    }

    public void sumarViandasPorHeladera(ViandasPorHeladera viandasPorHeladera){
        viandasPorHeladeras.add(viandasPorHeladera);
    }
}
