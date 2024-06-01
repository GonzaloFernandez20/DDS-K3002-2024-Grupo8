package TPAnual;

import java.util.Date;

public class Vianda {
    private String tipoDeComida;
    private Date fechaDeCaducidad;
    private Date fechaDeDonacion;
    private PersonaHumana colaborador;
    private Heladera heladera;
    private Float calorias;
    private Float peso;
    private EstadoVianda estado; // En manos de un homeless -> la heladera perdio una vianda
    
    public void trasladar(Heladera heladera){
        //
    }

    public void serEntregada() {
        estado = EstadoVianda.entregada;
    }
}

enum EstadoVianda {
    entregada,
    noEntregada,
    vencida
}