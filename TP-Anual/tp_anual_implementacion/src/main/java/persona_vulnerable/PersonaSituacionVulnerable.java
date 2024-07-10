package persona_vulnerable;

import colaborador.Colaborador;
import persona.Persona;
import persona.PersonaHumana;
import documentacion.Documento;
import localizacion.Direccion;
import heladera.Heladera;
import heladera.Vianda;
import sistema.Sistema;

import java.time.LocalDate;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class PersonaSituacionVulnerable {
    EstadoDeVivienda estadoDeVivienda;
    int cantMenores;
    Vinculacion vinculacion;
    PersonaHumana persona;

    public PersonaSituacionVulnerable(PersonaHumana persona, EstadoDeVivienda estado, int menores){
        this.estadoDeVivienda = estado;
        this.cantMenores = menores;
        this.persona = persona;
    }

    public void setCantMenores(int cantMenores) {
        this.cantMenores = cantMenores;
    }
    public void setVinculacion(Vinculacion unaVinculacion){
        this.vinculacion = unaVinculacion;
    }

    public Direccion getDomicilio() {
        return persona.getDireccion();
    }

    public EstadoDeVivienda getEstadoDeVivienda() {
        return estadoDeVivienda;
    }

    public int getCantMenores() {
        return cantMenores;
    }

    public Date getFechaDeNacimiento() {
        return persona.getFechaDeNacimiento();
    }

    public Documento getDocumento() {
        return persona.getDocumento();
    }

    public String getNombre() {
        return persona.getNombre();
    }

    public Vinculacion getVinculacion() {
        return vinculacion;
    }
}

class AccesoAHeladeras {
    PersonaSituacionVulnerable personaSituacionVulnerable;
    String id;
    int cantUsosRestantesPorDia;
    List <UsoXTarjeta> usos;

    public AccesoAHeladeras(PersonaSituacionVulnerable persona){
        this.personaSituacionVulnerable = persona;
        Sistema sistema = new Sistema();
        this.id = String.valueOf(sistema.getInstancia().getContadorDeVinculaciones());
        this.cantUsosRestantesPorDia = this.getCantUsosPorDia();
    }

    public int getCantUsosPorDia() {
        //Cada tarjeta sólo podrá ser utilizada cuatro veces en el mismo día,
        // y a su vez dos veces más por cada menor que tenga a cargo
        return 4 + personaSituacionVulnerable.getCantMenores() * 2; //hardcodeado por el enunciado
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
                //ERROR / EXCEPTION
            }else {
                usos.add(new UsoXTarjeta(heladera,
                        vianda,
                        LocalDateTime.now()));
                heladera.sacarVianda(vianda);
                cantUsosRestantesPorDia--;
            }
        }
        else {
            this.reiniciarCantidadDeUsos();
        }
    }

    public void reiniciarCantidadDeUsos(){
        this.cantUsosRestantesPorDia = this.getCantUsosPorDia();
    }

    public String ultimoAcceso(){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return this.ultimoUso().format(myFormatObj);
    }

    public String hoy(){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime hoy = LocalDateTime.now();
        return this.ultimoUso().format(myFormatObj);
    }

    public LocalDateTime ultimoUso(){
        UsoXTarjeta usoXTarjeta = usos.get(usos.size()-1);//el getLast no funcionaba para List
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