package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.incidentes.FallaTecnica;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.medios_de_contacto.WhatsApp;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;
import Repositorios.RepositorioHeladeras;
import Repositorios.RepositorioIncidentes;
import com.itextpdf.text.pdf.PdfPTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReporteDeFallas extends ReporteSemanal{
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
