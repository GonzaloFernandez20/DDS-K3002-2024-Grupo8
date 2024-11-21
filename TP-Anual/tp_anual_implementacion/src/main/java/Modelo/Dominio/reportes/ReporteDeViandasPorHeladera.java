package Modelo.Dominio.reportes;



import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.sistema.RegistroDeHeladeras;
import Repositorios.RepositorioAperturas;
import Repositorios.RepositorioHeladeras;
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
@Table(name = "ReporteViandasPorHeladera")
public class ReporteDeViandasPorHeladera extends ReporteSemanal{
    @OneToMany
    @JoinColumn(name = "id_reporte", referencedColumnName = "id_reporte")
    private List<ViandasPorHeladera> viandasPorHeladeras;

    public ReporteDeViandasPorHeladera(LocalDate fechaDeCreacion) {
        super(fechaDeCreacion);
        viandasPorHeladeras  = new ArrayList<>();
    }

    @Override
    public void completarReporte(){
        List<Heladera> heladerasConocidas = RepositorioHeladeras.getInstancia().getHeladeras();
        heladerasConocidas.forEach(heladera -> {
            ViandasPorHeladera viandasPorHeladera =
                    new ViandasPorHeladera(heladera,
                            RepositorioAperturas.getInstancia().cantidadDeRetirosDeHeladeraEntreFechas(heladera,LocalDate.now().minusWeeks(1),LocalDate.now()),
                            RepositorioAperturas.getInstancia().cantidadDeDepositosDeHeladeraEntreFechas(heladera,LocalDate.now().minusWeeks(1),LocalDate.now()));
            this.sumarViandasPorHeladera(viandasPorHeladera);
        });
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
