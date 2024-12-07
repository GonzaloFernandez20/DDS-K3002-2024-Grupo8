package Correo;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

//import java.util.ArrayList;
import java.util.List;

public class Camino {
    Integer idEnvio;
    List<SucursalXtiempo> recorrido; // LISTA DE SUCURSALES POR LAS QUE PASO

    public Camino(Integer idEnvio, List<SucursalXtiempo> recorrido){
        this.idEnvio = idEnvio;
        this.recorrido = recorrido;
    }

    public List<SucursalXtiempo> obtenerRecorrido() { return recorrido; }

    public void agregarSucursal(SucursalXtiempo sucursal) {
        this.recorrido.add(sucursal);
    }
    public void registrarTiempo(int sucursalActualComoIndice){ recorrido.get(sucursalActualComoIndice).ponerTimestamp();}
}

class SucursalXtiempo{
    Sucursal sucursal;
    LocalDateTime instanteDePaso;
    
    public SucursalXtiempo(Sucursal sucursal){
        this.sucursal = sucursal;
    }
            /* 
            LocalDate fecha = LocalDate.of(2024, 12, 15); 
            LocalTime hora = LocalTime.of(14, 30); 
            -> Asi se deberian pasar los parametros de fecha y hora al constructor en una instancia
            */

    // ? Getters para acceder a las propiedades
    public Sucursal sucursal() { return sucursal; }
    public LocalDateTime instanteDePaso() { return instanteDePaso; }
    public void ponerTimestamp(){instanteDePaso = LocalDateTime.now();}
}