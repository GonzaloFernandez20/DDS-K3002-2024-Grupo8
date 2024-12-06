package Repositorios;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.Dominio.contribucion.Producto;
import Modelo.Dominio.contribucion.Rubro;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;

import java.util.List;

public class RepositorioOfertas {
    private static RepositorioOfertas instancia;
    private static List<OfertaDeUnProducto> ofertas = List.of();

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
