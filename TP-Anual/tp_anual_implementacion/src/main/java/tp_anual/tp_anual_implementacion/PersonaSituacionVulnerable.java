package tp_anual.tp_anual_implementacion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.util.List;
import java.util.Objects;

public class PersonaSituacionVulnerable {
    String nombre;
    LocalDateTime fechaDeNacimiento;
    EstadoDeVivienda estadoDeVivienda;
    Direccion domicilio;
    Documento documento;
    int cantMenores;
    Vinculacion vinculacion;
    public PersonaSituacionVulnerable(String unNombre,LocalDateTime fecha,
                                      EstadoDeVivienda estado, Direccion unDomicilio,
                                      Documento unDcumento, int menores){
        this.nombre = unNombre;
        this.estadoDeVivienda = estado;
        this.fechaDeNacimiento = fecha;
        this.cantMenores = menores;
        this.domicilio = unDomicilio;
        this.documento = unDcumento;
    }

    public void setCantMenores(int cantMenores) {
        this.cantMenores = cantMenores;
    }
    public void setVinculacion(Vinculacion unaVinculacion){
        this.vinculacion = unaVinculacion;
    }

    public Direccion getDomicilio() {
        return domicilio;
    }

    public EstadoDeVivienda getEstadoDeVivienda() {
        return estadoDeVivienda;
    }

    public int getCantMenores() {
        return cantMenores;
    }

    public LocalDateTime getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Documento getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public Vinculacion getVinculacion() {
        return vinculacion;
    }
}

enum EstadoDeVivienda {
    poseeDomicilio,
    situacionDeCalle
}

class Vinculacion {
    PersonaHumana colaboradorQueRegistro;
    PersonaSituacionVulnerable personaSituacionVulnerable;
    LocalDateTime fechaRegistro;
    AccesoAHeladeras tarjetaEntregada;

    public Vinculacion(Colaborador colaborador, PersonaSituacionVulnerable persona, LocalDateTime fecha) {
        colaboradorQueRegistro = (PersonaHumana) colaborador;//revisar este cast
        personaSituacionVulnerable = persona;
        fechaRegistro = fecha;
        this.vincular(persona);
        //tarjetaEntregada = accesoAHeladeras; charlar si la vinculación incluye dar la tarjeta
    }
    public void vincular(PersonaSituacionVulnerable persona){
        persona.setVinculacion(this);
    }

    public void setTarjetaEntregada(AccesoAHeladeras tarjetaEntregada) {
        //no se si cualquiera tendría que acceder a este método
        this.tarjetaEntregada = tarjetaEntregada;
    }
    public AccesoAHeladeras getTarjetaEntregada() {
        return tarjetaEntregada;
    }
}

class AccesoAHeladeras {
    PersonaSituacionVulnerable personaSituacionVulnerable;
    String id;
    int cantUsosRestantesPorDia;
    List <UsoXTarjeta> usos;
    public AccesoAHeladeras(PersonaSituacionVulnerable persona, String id){
        this.personaSituacionVulnerable = persona;
        this.id = id;
        this.cantUsosRestantesPorDia = 4+ persona.getCantMenores();
    }

    public int getCantUsosPorDia() {
        //Cada tarjeta sólo podrá ser utilizada cuatro veces en el mismo día,
        // y a su vez dos veces más por cada menor que tenga a cargo
        return 4+ personaSituacionVulnerable.getCantMenores();
    }

    public int getCantUsosRestantesPorDia() {
        return cantUsosRestantesPorDia;
    }

    public String getId() {
        return id;
    }

    public PersonaSituacionVulnerable getPersonaSituacionVulnerable() {
        return personaSituacionVulnerable;
    }
    public void usar(Vianda vianda, Heladera heladera){
        if(Objects.equals(this.ultimoAcceso(),this.hoy())){
            if(cantUsosRestantesPorDia == 0){
                //ERROR / EXCEPTION ver mas tarde
            }else {
                usos.add(new UsoXTarjeta(heladera,
                        vianda,
                        LocalDateTime.now()));
                heladera.sacarVianda(vianda);
                cantUsosRestantesPorDia -= 1;
            }
        }
        else {
            this.reiniciarCantidadDeUsos();
        }
    }
    public void reiniciarCantidadDeUsos(){
        this.cantUsosRestantesPorDia = 4+ personaSituacionVulnerable.getCantMenores();
    }
    public String ultimoAcceso(){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String ultimoAcceso = this.ultimoUso().format(myFormatObj);
        return ultimoAcceso;
    }
    public String hoy(){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime hoy = LocalDateTime.now();
        String fechaDeHoy = this.ultimoUso().format(myFormatObj);
        return fechaDeHoy;
    }
    public LocalDateTime ultimoUso(){
        UsoXTarjeta usoXTarjeta = usos.get(usos.size()-1);//este agarra el último colocado o el último de la lista?
        return usoXTarjeta.getFecha();
    }
}

class UsoXTarjeta {
    Heladera heladera;
    Vianda vianda;
    LocalDateTime fecha;
    public UsoXTarjeta(Heladera heladera,Vianda vianda,LocalDateTime fecha){
        this.heladera = heladera;
        this.fecha = fecha;
        this.vianda = vianda;
    }
    public Heladera getHeladera() {
        return heladera;
    }
    public Vianda getVianda() {
        return vianda;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
}

class Documento{
    TipoDeDocumento tipo;
    int numero;
    Sexo sexo;
    public Documento(TipoDeDocumento tipo, int numero, Sexo sexo){
        this.setNumero(numero);
        this.setSexo(sexo);
        this.setTipo(tipo);
    }
    //no se si vale cambiar el número de documento y el sexo y etc, pero los setters publicos están
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setTipo(TipoDeDocumento tipo) {
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public TipoDeDocumento getTipo() {
        return tipo;
    }
}

enum TipoDeDocumento{
    LC,
    LE,
    CI,
    DNI,
    PASAPORTE
}

enum Sexo {
    FEMENINO,
    MASCULINO,
    OTRA
}