package Correo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class Correo {
    public static void main(String[] args) {
        // EMPLEADOS
        List<Cartero> carteros = new ArrayList<>();
        Cartero Juancito = new Cartero("Juan El Cartero", 556);
        carteros.add(Juancito);
        // ADMINISTRATIVOS
        List<Administrativo> administrativos = new ArrayList<>(); 
        Administrativo Jorge = new Administrativo("Jorge", 20);
        administrativos.add(Jorge);
        
        // SUCURSALES 
        Sucursal sucFlores = new Sucursal(0, null, null, null, null, null);
        Sucursal sucSanMartin = new Sucursal(0, null, null, null, null, null);
        
        // CAMINO RECORRIDO POR UN ENVIO
        List<SucursalXtiempo> recorridos = new ArrayList<>(); // USO UN ARRAY PARA PODER AGREGAR A LA COLECCION VARIAS VECES EL PASO POR UNA SUCURSAL

        recorridos.add(new SucursalXtiempo(sucFlores));
        recorridos.add(new SucursalXtiempo(sucSanMartin));
        recorridos.add(new SucursalXtiempo(sucFlores));

        Camino camino = new Camino(123, recorridos);
        
        // ENVIO
        
        Cliente ArcorSA = new Cliente();
        Cliente JuanManuel = new Cliente();

        List<Envio> envios = new ArrayList<>();
        Envio envio = new Envio(ArcorSA, JuanManuel, 56770.5, 7564, sucSanMartin, camino);
        
        envios.add(envio);

        // SUCURSAL PRINCIPAL
        Sucursal sucCaballito = new Sucursal(1, "Calle Falsa 123", "CABA", carteros, administrativos, envios);

        // ------------------------------------------------------------------------------------------------------------------------------------------------

        System.out.println("Envios para despachar: " + sucCaballito.enviosEnSucursal() );
        System.out.println("Recorrido del envio: " + envio.camino() );
        System.out.println("Cartero responsable de entrega: " + envio.carteroFinal );
    }
}

class Sucursal{
    int numeroDeSucursal;
    String domicilio;
    String localidad;
    List<Cartero> carteros;
    List<Administrativo> administrativo;
    List<Envio> enviosEnSucursal;

    // CONSTRUCTOR DE SUCURSAL
    public Sucursal(int numeroDeSucursal, 
                    String domicilio, 
                    String localidad, 
                    List<Cartero> carteros,
                    List<Administrativo> administrativos,
                    List<Envio> enviosEnSucursal) {
                        
        this.numeroDeSucursal = numeroDeSucursal;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.carteros = carteros;
        this.administrativo = administrativos;
        this.enviosEnSucursal = enviosEnSucursal;
    }
    // Consideramos que aquello que se recibe y se envía se llama envío. 
    public List<Envio> enviosEnSucursal(){
        return enviosEnSucursal;
    }
    public void recibirEnvio(Envio envio){ enviosEnSucursal.add(envio); }
    public void dejarIr(Envio envio){ enviosEnSucursal.remove(envio);}
    public void asignarCarteroA(Envio envio){
        int randomCartero = (int)(Math.random() * (carteros.size() + 1));  // Reviasr esto pls
        envio.asignarCartero(carteros.get(randomCartero));
    }

    /*
     * Pasamos el envío como parámetro en la sucursal porque consideramos que lo debemos instanciar previamente, con el objetivo de que ni 
     * el cliente ni la sucursal tengan que crear un envío, ya que no sería su responsabilidad. Surgió la posibilidad de que el cliente incluya 
     * un método entregarPedido(Sucursal sucursal, Envio envio) pero esta misma se descartó ya que su único objetivo sería pedirle a la sucursal 
     * que agregue dicho envío.
     */

}

class Central extends Sucursal{
    public Central(int numeroDeSucursal, String domicilio, List<Cartero> carteros, List<Administrativo> administrativos, List<Envio> enviosEnSucursal){
        super(numeroDeSucursal, domicilio, null, carteros, administrativos, enviosEnSucursal);
    }
    
    public Central(int numeroDeSucursal, String domicilio, List<Cartero> carteros,
                   List<Administrativo> administrativos, List<Envio> enviosEnSucursal) {
        super(numeroDeSucursal, domicilio, "Buenos Aires", carteros, administrativos, enviosEnSucursal);
    }
}
