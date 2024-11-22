package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.Sistema;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.itextpdf.text.pdf.PdfPTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "ReporteDeViandasPorColaborador")
public class ReporteDeViandasPorColaborador extends ReporteSemanal{
    @OneToMany
    @JoinColumn(name = "reporte", referencedColumnName = "id_reporte")
    private List<ViandasPorColaborador> viandasPorColaborador = new ArrayList<>();;

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
            ViandasPorColaborador viandasPorColaborador = new ViandasPorColaborador(colaborador, cantidadDeDonacionesDeViandas);
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

    public List<ViandasPorColaborador> getViandasPorColaborador() { return viandasPorColaborador; }
}
