package Modelo.Dominio.reportes;

import ServiceImpl.ReportesServiceImpl;
import com.itextpdf.text.pdf.PdfPTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ReporteDeFallas extends ReporteSemanal{
    private List<FallasPorHeladera> fallasPorHeladera = new ArrayList<FallasPorHeladera>();

    @Autowired
    ReportesServiceImpl reportesServiceImlp;

    @Override
    public void completarReporte(){
        List<FallasPorHeladera> fallasPorHeladerasBD = reportesServiceImlp.traerFallasPorHeladeras();
        this.fallasPorHeladera.addAll(fallasPorHeladerasBD);
        super.completarReporte();
    }

    public void sumarFallasPorheladera(FallasPorHeladera unaFallaPorHeladera){
        this.fallasPorHeladera.add(unaFallaPorHeladera);
    }

    @Override
    public List<String> obtenerNombreDeAtributosDelReporte() {
        return new ArrayList<>(Arrays.asList("Heladera", "Cantidad de Fallas"));
    }

    @Override
    public void completarTablaConAtributos(PdfPTable tabla){
        for(int i=0; i<fallasPorHeladera.size(); i++){
            FallasPorHeladera fallas = fallasPorHeladera.get(i);

            tabla.addCell(fallas.getHeladera().getUbicacion().getNombreCompletoDeUbicacion());
            tabla.addCell(String.valueOf(fallas.getCantidadDeFallas()));
        }
    }

    public List<FallasPorHeladera> getFallasPorHeladera() { return fallasPorHeladera; }
}
