package TPAnual;

interface Contribucion {
    public void efectuar();
}

class GestorDeContribuciones{
    /*     comentario:
                    Este recibe ya instanciada una contribucion y se encarga de efectuar();
                    Se decide que al igual que un colaborador, su instanciacion depende de un form. */
    private Contribucion contribucionActual;
    private Colaborador colaboradorActual;

    //Inyector de dependencias ->constructor
    public GestorDeContribuciones(Contribucion contribucion, Colaborador colaborador){
        this.contribucionActual = contribucion;
        this.colaboradorActual = colaborador;
    }

    private void realizarContribucion(){
        //contribucion.efectuar();
        //colaborador.sumarContribucion(contribucion);
    }
}

abstract class ContribucionExcluyente implements Contribucion{
    
    Colaborador colaborador; //Decidimos por diseño que como las excluyentes validan el colab este sea un atributo que tienen que usar para chequear permisos

    public void efectuar(){
        validar();
        contribucion();
    }

    public abstract void validar();
    public abstract void contribucion();
}

abstract class ContribucionHumana extends ContribucionExcluyente{
    @Override
    public void validar() {
        
    }
}

abstract class ContribucionJuridica extends ContribucionExcluyente{
    @Override
    public void validar() {
        
    }
}

class DonacionDeDinero implements Contribucion{
    Fecha fechaDeDonacion;
    int monto;
    String frecuencia;

    public void efectuar(){
        //
    }
}

class donacionDeVianda extends ContribucionHumana{
    Vianda viandas[];
    Heladera heladera;

    @Override
    public void contribucion() {
        //heladera.recibirViandas(vianditas);
    }
    
    private void agregarViandaADonacion(Vianda vianda){
        //viandas.add(vianda);
    }
}

class DistribucionDeVianda extends ContribucionHumana{
    Heladera heladeraDeOrigen;
    Heladera heladeraDestino;
    int cantDeViandas;
    MotivoDeDistribucion motivo;
    Fecha fechaDeDistribucion;

    @Override
    public void contribucion() {
        // viandasRetiradas = heladeraOrigen.retirarViandas(cantViandas);
        // heladeraDestino.recibirViandas(viandasRetiradas) 
        // foreach viandaRetiradas :: heladera = heladera destino
    }
}

enum MotivoDeDistribucion{
    desperfectoHeladera,
    faltaDeViandas
}

class HacerseCargoDeHeladera extends ContribucionJuridica{

    Heladera heladeraACargo;

    @Override
    public void contribucion() {
       heladeraACargo = new Heladera();
    }
}