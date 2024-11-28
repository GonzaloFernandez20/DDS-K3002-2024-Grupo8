package Modelo.Dominio.contribucion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "DonacionDeVianda")
public class DonacionDeViandas extends ContribucionConApertura {
    @OneToMany
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

    //Getters y Setters -----------------------------------------------------------------------------
    @Override
    public List<Vianda> getViandas() {return viandasDonadas;}
    public void setViandas(List<Vianda> viandasDonadas) {this.viandasDonadas = viandasDonadas;}
}