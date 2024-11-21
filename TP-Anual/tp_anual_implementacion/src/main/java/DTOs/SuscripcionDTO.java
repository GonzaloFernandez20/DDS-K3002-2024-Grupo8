package DTOs;

import java.util.List;

public class SuscripcionDTO {
    private Integer id_colaborador;
    private String nombreDelPunto;
    private String evento;

    public SuscripcionDTO(Integer id_colaborador, String nombreDelPunto, String evento) {
        this.id_colaborador = id_colaborador;
        this.nombreDelPunto = nombreDelPunto;
        this.evento = evento;
    }

    public Integer getId_colaborador() { return id_colaborador; }
    public String getNombreDelPunto() { return nombreDelPunto; }
    public String getEvento() { return evento; }
}
