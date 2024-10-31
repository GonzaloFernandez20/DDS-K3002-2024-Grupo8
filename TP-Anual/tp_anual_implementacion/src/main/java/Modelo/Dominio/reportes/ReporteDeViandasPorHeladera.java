package Modelo.Dominio.reportes;



import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.sistema.RegistroDeHeladeras;
import Repositorios.RepositorioAperturas;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ReporteViandasPorHeladera")
public class ReporteDeViandasPorHeladera extends ReporteSemanal{
    @OneToMany
    @JoinColumn(name = "id_reporte", referencedColumnName = "id_reporte")
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
