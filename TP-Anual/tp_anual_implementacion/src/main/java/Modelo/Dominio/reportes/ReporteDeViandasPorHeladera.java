package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.sistema.RegistroDeHeladeras;
import Repositorios.RepositorioAperturas;
import Repositorios.RepositorioHeladeras;
import com.itextpdf.text.pdf.PdfPTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReporteDeViandasPorHeladera extends ReporteSemanal{
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
            System.out.println("Heladera " + heladera.getIdHeladera());
            System.out.println("Depositos: " + viandasPorHeladera.getViandasColocadas());
            System.out.println("Retiros: " + viandasPorHeladera.getViandasRetiradas());
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
