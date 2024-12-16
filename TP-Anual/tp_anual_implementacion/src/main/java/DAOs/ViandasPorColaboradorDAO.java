package DAOs;

public class ViandasPorColaboradorDAO {
    private Integer id_colaborador;
    private Integer cantidad_de_viandas;

    public ViandasPorColaboradorDAO(Integer id_colaborador, Integer cantidad_de_viandas) {
        this.id_colaborador = id_colaborador;
        this.cantidad_de_viandas = cantidad_de_viandas;
    }

    public Integer getId_colaborador() {
        return id_colaborador;
    }

    public Integer getCantidad_de_viandas() {
        return cantidad_de_viandas;
    }
}
