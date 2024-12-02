package Modelo.Dominio.reportes;

import Modelo.Dominio.Repositories.heladera.HeladeraRepository;
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

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class ReporteDeFallas extends ReporteSemanal{
    private List<FallasPorHeladera> fallasPorHeladera = new ArrayList<FallasPorHeladera>();

    //Inyección dedependencias actual que funciona pero es medio desprolija
    private HeladeraRepository heladeraRepository;

    public void setHeladeraRepository(HeladeraRepository heladeraRepository) {
        this.heladeraRepository = heladeraRepository;
    }

    //Inyección de dependencias ideal
    //@Autowired
    //private HeladeraRepository heladeraRepository;
    public ReporteDeFallas() {
    }

    //MELI, PROBÁ ÉSTE MÉTODO, SI FUNCIONA EL RESTO SALE SOLO

    public void metodoDeMELI(){
        List<Heladera> heladeras = heladeraRepository.findAll();
        System.out.println(Arrays.toString(heladeras.stream().map(heladera -> heladera.getId_heladera()).toArray()));
    }





    @Override
    public void completarReporte(){
        //Acceder al repo y traer los datos
        Map<Integer, Integer> id_heladera_y_fallas = heladeraRepository.traerReportesDeFallasEntreFechas(LocalDate.now().minusWeeks(1), LocalDate.now());
        List<Integer> ids_de_heladeras = new ArrayList<Integer>(id_heladera_y_fallas.values());
        List<Heladera> heladeras = new ArrayList<Heladera>(heladeraRepository.findAllById(ids_de_heladeras));
        for (Map.Entry<Integer, Integer> map : id_heladera_y_fallas.entrySet()) {
            Heladera heladera = heladeras.stream().filter(heladera_en_lista -> heladera_en_lista.getIdHeladera() == map.getKey()).toList().get(0);
            //Completo el FallasPorHeladera
            FallasPorHeladera cantFallasPorHeladera = new FallasPorHeladera();
            cantFallasPorHeladera.setHeladera(heladera);
            cantFallasPorHeladera.setCantidadDeFallas(map.getValue());

            this.sumarFallasPorheladera(cantFallasPorHeladera);

            //Elimino la haladera para que la próxima lista sea un poco más corta y trabaje más rápido
            heladeras.remove(heladera);
        }
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
