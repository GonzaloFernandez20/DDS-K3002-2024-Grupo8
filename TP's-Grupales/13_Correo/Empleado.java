package Correo;
import java.util.List;


public class Empleado {
    String nombre;
    Integer id; 
 
    public Empleado(String nombre, Integer id){ // CONSTRUCTOR DE LA CLASE
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser null.");
        }
        if ( id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo y no nulo.");
        }
        this.nombre = nombre;
        this.id = id;
    }

    /* Justificacion de diseño 
    ¿Por qué usar Integer en lugar de int?
    - Permite null: A diferencia de int, Integer es una clase envolvente para el tipo primitivo int y puede contener null.
     Esto puede ser útil si en tu aplicación un valor null tiene un significado especial, como la ausencia de un valor.

    - Flexibilidad: Usar Integer proporciona flexibilidad adicional, como la capacidad de utilizar métodos pertenecientes
     a la clase Integer o usar null para representar estados no inicializados o desconocidos sin necesidad de
     recurrir a valores especiales como -1.
     */
}

class Cartero extends Empleado{

    List<Envio> pedidosAEntregar;
    public Cartero(String nombre, Integer id) {
        super(nombre, id);  // LLAMA AL CONSTRUCTOR DE EMPLEADO
    }

    public void recogerEnvio(Envio envio){
        pedidosAEntregar.add(envio);
    }

    public void entregarEnvio(Envio envio) throws Exception {
        if(pedidosAEntregar.contains(envio)){
            pedidosAEntregar.remove(envio);
            envio.entregaFinal(this);
            envio.entregar();
        }
        else {
            throw new Exception("No tengo ese envio");
        }
    }
}

class Administrativo extends Empleado{
    public Administrativo(String nombre, Integer id) {
        super(nombre, id);  // LLAMA AL CONSTRUCTOR DE EMPLEADO
    }
}