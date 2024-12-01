package Modelo.Dominio.Accesos_a_heladeras;

import Modelo.Dominio.Persona.Persona;
import Modelo.Dominio.Persona.PersonaHumana;
import jakarta.persistence.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SolicitudTarjeta")
public class SolicitudTarjeta {
    @Id
    @GeneratedValue
    private Integer id_solicitud_tarjeta;
    @ManyToOne
    @JoinColumn(name = "destinatario", referencedColumnName = "id_persona")
    private Persona destinatario;
    @Column(name = "cantidad_de_tarjetas")
    private int cantidadDeTarjetas;
    private List<String> codigos = new ArrayList<>();

    public SolicitudTarjeta(PersonaHumana destinatario, int cantidadDeTarjetas) {
        this.destinatario = destinatario;
        this.cantidadDeTarjetas = cantidadDeTarjetas;
        codigos = this.generarCodigos();
    }

    public List<String> generarCodigos() {
        for(int i = 0; i < this.cantidadDeTarjetas; i++) {
            String codigo = this.generateRandomString(8);
            System.out.println(codigo);
            codigos.add(codigo);
        }
        return codigos;
    }

    public List<String> getCodigos() {
        return codigos;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public Persona getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(PersonaHumana destinatario) {
        this.destinatario = destinatario;
    }

    public int getCantidadDeTarjetas() {
        return cantidadDeTarjetas;
    }

    public void setCantidadDeTarjetas(int cantidadDeTarjetas) {
        this.cantidadDeTarjetas = cantidadDeTarjetas;
    }

    public boolean esElMismoDestinatario(Persona persona) {
        return this.destinatario.equals(persona);
    }

    public boolean esLaMismaTarjeta(String codigoTarjeta) {
        return this.codigos.contains(codigoTarjeta);
    }
}
