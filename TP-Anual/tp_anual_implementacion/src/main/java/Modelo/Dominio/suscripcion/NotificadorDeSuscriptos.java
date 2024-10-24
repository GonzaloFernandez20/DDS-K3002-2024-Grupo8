package Modelo.Dominio.suscripcion;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.heladera.Heladera;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name = "NotificadorDeSuscriptos")
public class NotificadorDeSuscriptos {
    @Id
    @GeneratedValue
    private Integer id_notificador_de_suscriptos;
    @ElementCollection
    @CollectionTable(name = "subscriptos_map", joinColumns = @JoinColumn(name = "id_notificador_de_suscriptos"))
    @MapKeyColumn(name = "subscriber_key")
    @OneToMany(mappedBy = "id_suscriptos_por_evento", cascade = CascadeType.ALL)
    private Map<String, SuscriptosPorEvento> suscriptos;
    // Hibernate no me deja mepear Map<String, List<Colaborador>> por tener List<Colaborador>
    @OneToOne
    @JoinColumn(name = "id_heladera", referencedColumnName = "id_heladera")
    private Heladera heladera;

    public NotificadorDeSuscriptos(Heladera heladera) {
        this.heladera = heladera;
        this.suscriptos = new HashMap<>();
    }

    public void suscribir(String evento, Colaborador colaborador){
        suscriptos.computeIfAbsent(evento, key -> new SuscriptosPorEvento());
        suscriptos.get(evento).addColaboradorASuscripcion(colaborador);
    }

    public void desuscribir(String evento, Colaborador colaborador){
        suscriptos.get(evento).removeColaboradorASuscripcion(colaborador);
        if (suscriptos.get(evento).getColaboradoresSuscriptos().isEmpty()) suscriptos.remove(evento);
    }

    public void notificar(String evento){ // A cada colaborador suscripto a ese evento, se le envia un mensaje
        String mensaje = CreadorDeMensajes.crearMensaje(evento, heladera);
        if(suscriptos.containsKey(evento)){
            for (Colaborador suscripto : suscriptos.get(evento).getColaboradoresSuscriptos()){
                suscripto.notificar(mensaje);
            }
        }
    }

    // ---- Getters y Setters
//    public Map<String, List<Colaborador>> getSuscriptos() { return suscriptos; }
    public Map<String, List<Colaborador>> getSuscriptos() {
        //El método queda así porque el resto del modelado lo espera un Map<String, List<Colaborador>>
        return this.transformarDePersistnciaAModelo();
    }
    private Map<String, List<Colaborador>> transformarDePersistnciaAModelo(){
        Map<String, List<Colaborador>> suscriptosTransformados = new HashMap<>();
        this.suscriptos.forEach( (String evento, SuscriptosPorEvento suscriptosPorEvento) ->
        {
                suscriptosTransformados.computeIfAbsent(evento, key -> new ArrayList<>());
                suscriptosTransformados.get(evento).addAll(suscriptosPorEvento.getColaboradoresSuscriptos());
        });
        return suscriptosTransformados;
    }

}
@Entity
@Table(name = "SuscriptosPorEvento")
class SuscriptosPorEvento{
    @Id
    @GeneratedValue
    private Integer id_suscriptos_por_evento;
    @OneToMany
    @JoinColumn(name = "id_colaborador", referencedColumnName = "id_colaborador")
    private List<Colaborador> colaboradoresSuscriptos;

    public SuscriptosPorEvento() {
        this.colaboradoresSuscriptos = new ArrayList<>();
    }

    public List<Colaborador> getColaboradoresSuscriptos() {
        return colaboradoresSuscriptos;
    }

    public void setColaboradoresSuscriptos(List<Colaborador> colaboradoresSuscriptos) {
        this.colaboradoresSuscriptos = colaboradoresSuscriptos;
    }

    public void addColaboradorASuscripcion(Colaborador colaborador){
        colaboradoresSuscriptos.add(colaborador);
    }

    public void removeColaboradorASuscripcion(Colaborador colaborador){
        colaboradoresSuscriptos.remove(colaborador);
    }
}
