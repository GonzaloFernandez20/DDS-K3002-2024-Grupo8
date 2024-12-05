package DTOs;

import Modelo.Dominio.heladera.EstadoHeladera;

public class HeladeraSeleccionDTO {
    private int idHeladera;
    private String nombreDelPunto;
    private String calle;
    private String altura;
    private String ciudad;
    private int stockDeViandas;
    private int capacidadRestante;
    private EstadoHeladera estado;

    public HeladeraSeleccionDTO(int idHeladera,
                                String nombreDelPunto,
                                String calle,
                                String altura,
                                String ciudad,
                                int stockDeViandas,
                                int capacidadRestante,
                                EstadoHeladera estado) {
        this.idHeladera = idHeladera;
        this.nombreDelPunto = nombreDelPunto;
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.stockDeViandas = stockDeViandas;
        this.capacidadRestante = capacidadRestante;
        this.estado = estado;
    }

    public int getIdHeladera() { return idHeladera; }
    public String getNombreDelPunto() { return nombreDelPunto; }
    public String getCalle() { return calle; }
    public String getAltura() { return altura; }
    public String getCiudad() { return ciudad; }
    public int getStockDeViandas() { return stockDeViandas; }
    public int getCapacidadRestante() { return capacidadRestante; }
    public EstadoHeladera getEstado() { return estado; }
}
