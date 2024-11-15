package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.Sistema;
import com.itextpdf.text.pdf.PdfPTable;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReporteDeViandasPorColaborador extends ReporteSemanal{
    private List<ViandasPorColaborador> viandasPorColaborador;

    public ReporteDeViandasPorColaborador(LocalDate fechaDeCreacion) {
        super(fechaDeCreacion);
        viandasPorColaborador  = new ArrayList<>();
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
            sumarViandasPorColaborador(viandasPorColaborador);
        });
        super.completarReporte();
    }

    @Override
    public List<String> obtenerNombreDeAtributosDelReporte() {
        return new ArrayList<>(Arrays.asList("Id Colaborador", "Cantidad de Viandas"));
    }

    @Override
    public void completarTablaConAtributos(PdfPTable tabla){
        for(int i=0; i<viandasPorColaborador.size(); i++){
            ViandasPorColaborador viandas = viandasPorColaborador.get(i);

            tabla.addCell(String.valueOf(viandas.getColaborador().getId_colaborador()));
            tabla.addCell(String.valueOf(viandas.getCantidadDeViandas()));
        }
    }
}
