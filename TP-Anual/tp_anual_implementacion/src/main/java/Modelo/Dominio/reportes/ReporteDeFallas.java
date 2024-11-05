package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.sistema.RegistroDeHeladeras;
import Repositorios.RepositorioAperturas;
import Repositorios.RepositorioHeladeras;
import Repositorios.RepositorioIncidentes;
import com.itextpdf.text.pdf.PdfPTable;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReporteDeFallas extends ReporteSemanal{
    private List<FallasPorHeladera> fallasPorHeladera;

    public ReporteDeFallas(LocalDate fechaDeCreacion) {
        super(fechaDeCreacion);
    }
    @Override
    public void completarReporte(){
        List<Heladera> heladerasConocidas = RepositorioHeladeras.getInstancia().getHeladeras();
        heladerasConocidas.forEach(heladera -> {
            FallasPorHeladera fallasPorHeladera =
                    new FallasPorHeladera(heladera,
                            RepositorioIncidentes.getInstancia().getFallasTecnicasDeHeladeraEntreFechas(heladera, LocalDate.now().minusWeeks(1), LocalDate.now()).size());
            this.sumarFallasPorheladera(fallasPorHeladera);
        });
        super.completarReporte();
    }

    public void sumarFallasPorheladera(FallasPorHeladera unaFallaPorHeladera){
        fallasPorHeladera.add(unaFallaPorHeladera);
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
}
