package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Entity
@Table(name = "DonacionDeVianda")
public class DonacionDeViandas extends ContribucionConApertura {
    @OneToMany( cascade = CascadeType.ALL,  fetch = FetchType.EAGER )
    @JoinColumn(name = "donacion", referencedColumnName = "id_contribucion")
    private List<Vianda> viandasDonadas;
    //Constructores------------------------------------------------------------------------------------------------------------
    public DonacionDeViandas(Colaborador colaborador, Heladera heladera, List <Vianda> viandas, LocalDate fechaDeContribucion) {
        this.colaborador = colaborador;
        this.fechaDeContribucion = fechaDeContribucion;
        this.heladeraDestino = heladera;
        this.viandasDonadas = viandas;
    }

    public DonacionDeViandas() {}

    //Métodos ---------------------------------
    @Override
    public double puntosQueSumaColaborador() {
        double coeficiente = 1.5;
        return viandasDonadas.size() * coeficiente;
    }
    @Override
    public void loggear(){
        log.info("La {} ID:{}recibió una donación de viandas.", heladeraDestino.getNombreDelPunto(), heladeraDestino.getid_heladera());

        String viandas = viandasDonadas.stream()
                .map(Vianda::getTipoDeComida) // Obtener el tipo de comida
                .collect(Collectors.joining(", "));
        log.info("Viandas ingresadas: {}", viandas);
    }



    //Getters y Setters -----------------------------------------------------------------------------
    @Override
    public List<Vianda> getViandas() {return viandasDonadas;}
    public void setViandas(List<Vianda> viandasDonadas) {this.viandasDonadas = viandasDonadas;}
}