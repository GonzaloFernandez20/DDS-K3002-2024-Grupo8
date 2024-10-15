package Repositorios;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.Dominio.contribucion.Producto;
import Modelo.Dominio.contribucion.Rubro;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.persona.PersonaJuridica;
import Modelo.Dominio.persona.TipoOrganizacion;

import java.util.List;

public class RepositorioOfertas {
    private static RepositorioOfertas instancia;
    private static List<OfertaDeUnProducto> ofertas = List.of(
            // HARDCODEADO HASTA PERSISTENCIA
            new OfertaDeUnProducto(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), "Campana", 50, "/img/notifications-icon.svg", Rubro.JUGUETERIA, new Producto("Campana", 1)),
            new OfertaDeUnProducto(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50", "1010")), List.of(new Mail("gastronomosargentinos@gmail.com"))), "Lámpara Blanca", 250, "/img/dar.png", Rubro.ARTICULOS_PARA_EL_HOGAR, new Producto("Lámpara blanca", 50))
    );

    public static RepositorioOfertas getInstancia(){
        if(instancia == null){
            instancia = new RepositorioOfertas();
        }

        return instancia;
    }

    public List<OfertaDeUnProducto> getOfertas(){ return ofertas; }

    public OfertaDeUnProducto buscarOfertaPorId(int idBuscado) {
        return ofertas.stream().filter(oferta -> oferta.getIdOferta() == idBuscado).findFirst().orElse(null);
    }
}
