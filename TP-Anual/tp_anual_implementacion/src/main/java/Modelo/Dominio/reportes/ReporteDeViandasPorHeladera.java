package Modelo.Dominio.reportes;

import com.itextpdf.text.pdf.PdfPTable;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ReporteDeViandasPorHeladera extends ReporteSemanal{
    private List<ViandasPorHeladera> viandasPorHeladeras = new ArrayList<ViandasPorHeladera>();

//    @Autowired
//    ReportesServiceImpl reportesServiceImlp;

    public ReporteDeViandasPorHeladera(LocalDate fechaDeCreacion) {
        super(fechaDeCreacion);
        viandasPorHeladeras  = new ArrayList<>();
    }

    public ReporteDeViandasPorHeladera() {
       super();
    }

    @Override
    public void completarReporte(){
        List<ViandasPorHeladera> viandasPorHeladeraBD = reportesServiceImlp.traerViandasPorHeladera();
        this.viandasPorHeladeras.addAll(viandasPorHeladeraBD);
        super.completarReporte();
    }

    public void sumarViandasPorHeladera(ViandasPorHeladera viandasPorHeladera){
        viandasPorHeladeras.add(viandasPorHeladera);
    }

    @Override
    public List<String> obtenerNombreDeAtributosDelReporte() {
        return new ArrayList<>(Arrays.asList("Heladera", "Viandas Colocadas", "Viandas Retiradas"));
    }

    @Override
    public void completarTablaConAtributos(PdfPTable tabla) {
        for(int i=0; i<viandasPorHeladeras.size(); i++) {
            ViandasPorHeladera viandas = viandasPorHeladeras.get(i);

            tabla.addCell(viandas.getHeladera().getUbicacion().getNombreCompletoDeUbicacion());
            tabla.addCell(String.valueOf(viandas.getViandasColocadas()));
            tabla.addCell(String.valueOf(viandas.getViandasRetiradas()));
        }
    }

    public List<ViandasPorHeladera> getViandasPorHeladeras() { return viandasPorHeladeras; }
}
