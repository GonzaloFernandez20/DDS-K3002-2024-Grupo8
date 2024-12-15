package DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeladeraSuscripcionDTO {
    private int idHeladera;
    private String nombreDelPunto;
    private String calle;
    private String altura;
    private String ciudad;

    //Informacion sobre las suscripciones
    private boolean suscritoAFallas;
    private int cantidadQuedanNViandas;
    private int cantidadFaltanNViandas;

    public HeladeraSuscripcionDTO(int idHeladera,
                                  String nombreDelPunto,
                                  String calle,
                                  String altura,
                                  String ciudad,
                                  boolean suscritoAFallas,
                                  int cantidadQuedanNViandas,
                                  int cantidadFaltanNViandas) {
        this.idHeladera = idHeladera;
        this.nombreDelPunto = nombreDelPunto;
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
        this.suscritoAFallas = suscritoAFallas;
        this.cantidadQuedanNViandas = cantidadQuedanNViandas;
        this.cantidadFaltanNViandas = cantidadFaltanNViandas;
    }
}
