package Modelo.Dominio.reportes;

import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

@Entity
@Table(name = "ViandasPorHeladera")
public class ViandasPorHeladera{
    @Id
    @GeneratedValue
    private Integer id_viandas_por_heladera;
    @OneToOne
    @JoinColumn(name = "id_heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;
    @Column(name = "viandas_retiradas")
    private Integer ViandasRetiradas;
    @Column(name = "viandas_colocadas")
    private Integer ViandasColocadas;

    public ViandasPorHeladera(Heladera heladera, Integer viandasRetiradas, Integer viandasColocadas) {
        this.heladera = heladera;
        ViandasRetiradas = viandasRetiradas;
        ViandasColocadas = viandasColocadas;
    }
}
