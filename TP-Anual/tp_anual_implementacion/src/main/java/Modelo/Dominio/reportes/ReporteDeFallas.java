package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;
import Repositorios.RepositorioHeladeras;
import Repositorios.RepositorioIncidentes;
import com.itextpdf.text.pdf.PdfPTable;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "ReporteFallas")
public class ReporteDeFallas extends ReporteSemanal{
    @OneToMany
    @JoinColumn(name = "reporte", referencedColumnName = "id_reporte")
    private List<FallasPorHeladera> fallasPorHeladera;

    public ReporteDeFallas(LocalDate fechaDeCreacion) {
        super(fechaDeCreacion);
        fallasPorHeladera = new ArrayList<>();
    }

    @Override
    public void completarReporte(){
        List<Heladera> heladerasConocidas = RepositorioHeladeras.getInstancia().getHeladeras();
        heladerasConocidas.forEach(heladera -> {
            FallasPorHeladera fallasPorHeladera = new FallasPorHeladera(heladera, RepositorioIncidentes.getInstancia()
                    .getFallasTecnicasDeHeladeraEntreFechas(heladera, LocalDate.now().minusWeeks(1), LocalDate.now()).size());
            this.sumarFallasPorheladera(fallasPorHeladera);
            System.out.println(fallasPorHeladera.getHeladera().getUbicacion().getNombreCompletoDeUbicacion());
            System.out.println(fallasPorHeladera.getCantidadDeFallas());
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

    public List<FallasPorHeladera> getFallasPorHeladera() { return fallasPorHeladera; }
}
