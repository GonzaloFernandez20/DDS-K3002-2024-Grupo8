package DAOs;

import lombok.Data;


public class FallasPorHeladeraDAO {
    Integer id_heladera;
    Integer cantidad_de_fallas;

    public FallasPorHeladeraDAO(Integer id_heladera, Integer cantidad_de_fallas) {
        this.id_heladera = id_heladera;
        this.cantidad_de_fallas = cantidad_de_fallas;
    }

    public FallasPorHeladeraDAO() {
    }

    public Integer getId_heladera() {
        return id_heladera;
    }

    public Integer getCantidad_de_fallas() {
        return cantidad_de_fallas;
    }

}
