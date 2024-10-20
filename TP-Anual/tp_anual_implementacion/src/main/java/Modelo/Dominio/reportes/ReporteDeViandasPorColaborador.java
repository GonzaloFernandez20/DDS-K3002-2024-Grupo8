package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.Sistema;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ReporteDeViandasPorColaborador")
public class ReporteDeViandasPorColaborador extends ReporteSemanal{
    @OneToMany
    @JoinColumn(name = "id_reporte", referencedColumnName = "id_reporte")
    private List<ViandasPorColaborador> viandasPorColaborador;

    public ReporteDeViandasPorColaborador(LocalDate fechaDeCreacion) {
        super(fechaDeCreacion);
    }

    public void sumarViandasPorColaborador(ViandasPorColaborador unaViandaPorColaborador){
        viandasPorColaborador.add(unaViandaPorColaborador);
    }
    @Override
    public void completarReporte(){
        List<Colaborador> colaboradoresConocidos = Sistema.getInstancia().getColaboradores();
        colaboradoresConocidos.forEach(colaborador -> {
            Integer cantidadDeDonacionesDeViandas = colaborador.cantidadDeDonacionesDeViandaEntre(LocalDate.now().minusWeeks(1), LocalDate.now());
            ViandasPorColaborador viandasPorColaborador = new ViandasPorColaborador(colaborador,cantidadDeDonacionesDeViandas);
        });
    }
}
