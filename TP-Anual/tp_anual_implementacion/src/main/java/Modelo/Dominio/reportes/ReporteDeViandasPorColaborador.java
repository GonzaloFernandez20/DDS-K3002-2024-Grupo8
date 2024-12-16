package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.sistema.Sistema;

import ServiceImpl.ReportesServiceImpl;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ReporteDeViandasPorColaborador extends ReporteSemanal{
    private List<ViandasPorColaborador> viandasPorColaborador = new ArrayList<>();;

//    @Autowired
//    ReportesServiceImpl reportesServiceImlp;

    public void sumarViandasPorColaborador(ViandasPorColaborador unaViandaPorColaborador){
        viandasPorColaborador.add(unaViandaPorColaborador);
    }
    @Override
    public void completarReporte(){
        List<ViandasPorColaborador> viandasPorColaboradorsBD = reportesServiceImlp.traerViandasPorColaborador();
        this.viandasPorColaborador.addAll(viandasPorColaboradorsBD);
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
