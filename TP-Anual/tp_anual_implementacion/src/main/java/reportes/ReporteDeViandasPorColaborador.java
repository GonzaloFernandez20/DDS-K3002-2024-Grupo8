package reportes;

import colaborador.Colaborador;
import sistema.Sistema;

import java.time.LocalDate;
import java.util.List;

public class ReporteDeViandasPorColaborador extends ReporteSemanal{
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
