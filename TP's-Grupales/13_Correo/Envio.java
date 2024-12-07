package Correo;
import java.util.List;

public class Envio {
    Cliente destinatario;
    Cliente remitente;
    double precio;
    Integer idEnvio;
    Sucursal sucursalActual;
    Cartero carteroFinal;
    Camino camino;
    int numeroDeSucursal = 0;
    boolean entregado = false;
    public Envio(Cliente destinatario, Cliente remitente, Double precio, Integer idEnvio, 
    Sucursal sucursalActual, Camino camino) {
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.precio = precio;
        this.idEnvio = idEnvio;
        this.camino = camino;
        this.sucursalActual = camino.obtenerRecorrido().get(numeroDeSucursal).sucursal();
    }

    /*
     * Creamos la variable numeroDeSucursales para poder modificar la sucursal en la que se encuentra el camino. Para ello, 
     * recorremos la lista Camino, que es la que tiene las diferentes sucursales por las que debe pasar el envío. 

     */
    // ! Metodos
    public List<SucursalXtiempo> caminoRecorrido(){ return camino.obtenerRecorrido(); }
    public void entregar(){ entregado = true; }
    public void entregaFinal(Cartero cartero){ carteroFinal = cartero; }
    public void enviarAProximaSucursal(){
        numeroDeSucursal += 1;
        if(camino.obtenerRecorrido().size() <= numeroDeSucursal) {
            sucursalActual.dejarIr(this);//largo el envio de donde se ubica
            sucursalActual = camino.obtenerRecorrido().get(numeroDeSucursal).sucursal();//actualizo su ubicacion
            sucursalActual.recibirEnvio(this);//hago que se acepte el envio en otro lado
            camino.registrarTiempo(numeroDeSucursal);
        }
        else{sucursalActual.asignarCarteroA(this);}
    }
    public void asignarCartero(Cartero cartero){
        carteroFinal = cartero;
        carteroFinal.recogerEnvio(this);
    }
    public Sucursal sucursalActual(){ return sucursalActual;}
    public Camino camino(){return camino;}
}

// ? TIPOS DE ENVIOS

class Telegrama extends Envio{
    String contenido; //Esto deberia ser un archivo...
    TipoDeTelegrama tipoDeTelegrama;

    // CONSTRUCTOR PARA QUE NO SE PUEDA INICIALIZAR SIN UN TELEGRAMA
    public Telegrama(Cliente destinatario, Cliente remitente, Double precio, Integer idEnvio, 
                     Sucursal sucursalActual, Camino camino,
                     TipoDeTelegrama telegrama){
        super(destinatario, remitente, precio, idEnvio, sucursalActual, camino);
        if (telegrama == null) {
            throw new IllegalArgumentException("Debe ingresar el tipo de telegrama correcto");
        }
        this.tipoDeTelegrama = telegrama;
    }

    public void contenido(String contenido){
        this.contenido = contenido;
    }
}
/*
 * Utilizamos los tipos de datos Enum para describir tipos de un dato determinado, como es por ejemplo carta documento de Telegrama.
 */

enum TipoDeTelegrama{
    CartaDocumento,
    TelegramaPorEvento,
    Invitacion;
}

class Giro extends Envio{
    Float importe;

    public Giro(Cliente destinatario, Cliente remitente,Double precio, Integer idEnvio, 
                Sucursal sucursalActual, Camino camino){
        super(destinatario, remitente, precio, idEnvio, sucursalActual, camino);
    }
}

class Carta extends Envio{
    Sello tipoDeSello;
    TipoDeCarta tipoDeCarta;

    public Carta(Cliente destinatario, Cliente remitente,Double precio, Integer idEnvio, 
                 Sucursal sucursalActual, Camino camino,
                 TipoDeCarta tipoDeCarta){
        super(destinatario, remitente, precio, idEnvio, sucursalActual, camino);
        if (tipoDeCarta == null) {
            throw new IllegalArgumentException("Debe ingresar el tipo de telegrama correcto");
        }
        this.tipoDeCarta = tipoDeCarta;
    }
}
enum TipoDeCarta{
    Expresa,
    Simple,
    Certificada;
}
class Sello{}
/*
 * Las clases SelloRojo y SelloNegro las creamos, en vez de tener únicamente la clase Sello ya que parecerían tener comportamiento distinto.

 */
class SelloRojo extends Sello{} // !-> Por terminos de codeo capaz conviene que sean enums
class SelloNegro extends Sello{}

class Encomienda extends Envio{
    TipoDeEncomienda tipoDeEncomienda;
    
    public Encomienda(Cliente destinatario, Cliente remitente, Double precio, Integer idEnvio, 
                      Sucursal sucursalActual, Camino camino,
                      TipoDeEncomienda tipoDeEncomienda){
        super(destinatario, remitente, precio, idEnvio, sucursalActual, camino);
        if (tipoDeEncomienda == null) {
            throw new IllegalArgumentException("Debe ingresar el tipo de telegrama correcto");
        }
        this.tipoDeEncomienda = tipoDeEncomienda;
    }
}
enum TipoDeEncomienda{
    Personal,
    Paquete;
}



