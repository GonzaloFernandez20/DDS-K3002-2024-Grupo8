package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

@Entity
@Table(name = "FallasPorHeladera")
public class FallasPorHeladera {
    @Id
    @GeneratedValue
    private Integer id_fallas_por_heladera;
    @ManyToOne
    @JoinColumn(name = "heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;
    @Column(name = "cantidad_de_fallas")
    private Integer cantidadDeFallas;

    public FallasPorHeladera(Heladera heladera, Integer cantidadDeFallas) {
        this.heladera = heladera;
        this.cantidadDeFallas = cantidadDeFallas;
    }

    public Heladera getHeladera() { return heladera; }
    public Integer getCantidadDeFallas() { return cantidadDeFallas; }
}
