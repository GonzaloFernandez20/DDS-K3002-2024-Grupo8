package Modelo.Dominio.reportes;



import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.sistema.RegistroDeHeladeras;
import Repositorios.RepositorioAperturas;

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
                            RepositorioAperturas.getInstancia().cantidadDeDepositosDeHeladeraEntreFechas(heladera,LocalDate.now().minusWeeks(1),LocalDate.now()),
                            RepositorioAperturas.getInstancia().cantidadDeRetirosDeHeladeraEntreFechas(heladera,LocalDate.now().minusWeeks(1),LocalDate.now()));
            this.sumarViandasPorHeladera(viandasPorHeladera);
        });
    }

    public void sumarViandasPorHeladera(ViandasPorHeladera viandasPorHeladera){
        viandasPorHeladeras.add(viandasPorHeladera);
    }
}
