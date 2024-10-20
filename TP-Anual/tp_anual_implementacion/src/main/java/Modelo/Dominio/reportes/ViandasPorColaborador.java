package Modelo.Dominio.reportes;

import Modelo.Dominio.colaborador.Colaborador;
import jakarta.persistence.*;

@Entity
@Table(name = "ViandasPorColaborador")
public class ViandasPorColaborador{
    @Id
    @GeneratedValue
    private Integer id_viandas_por_colaborador;
    @OneToOne
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    private Colaborador colaborador;
    @Column(name = "cantidad_de_viandas")
    private Integer cantidadDeViandas;

    public ViandasPorColaborador(Colaborador colaborador, Integer cantidadDeViandas) {
        this.colaborador = colaborador;
        this.cantidadDeViandas = cantidadDeViandas;
    }
}
