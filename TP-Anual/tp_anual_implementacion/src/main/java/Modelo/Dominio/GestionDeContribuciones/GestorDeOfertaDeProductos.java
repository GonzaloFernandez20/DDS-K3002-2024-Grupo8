package Modelo.Dominio.GestionDeContribuciones;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;

public class GestorDeOfertaDeProductos {
    private Colaborador colaborador;
    private OfertaDeUnProducto ofertaDeUnProducto;

    public static void crearContribucion(Colaborador colaborador, OfertaDeUnProducto ofertaDeUnProducto) {
        ofertaDeUnProducto.procesarLaContribucion();
    }

    public static void canjearProducto(Colaborador colaborador, OfertaDeUnProducto ofertaDeUnProducto) throws Exception {
        validarCanje(colaborador, ofertaDeUnProducto);
        ofertaDeUnProducto.serCanjeada();
        colaborador.canjearPuntos( ofertaDeUnProducto.getPuntosNecesarios() );
    }

    private static void validarCanje(Colaborador colaborador, OfertaDeUnProducto ofertaDeUnProducto) throws Exception {
        // Validar que la oferta tenga suficiente stock
        if (!ofertaDeUnProducto.hayStock()) {
            throw new Exception("La oferta no tiene stock suficiente para el canje.");
        }
        // Validar que el colaborador tenga suficientes puntos
        if (colaborador.getPuntosAcumulados() < ofertaDeUnProducto.getPuntosNecesarios()) {
            throw new Exception("El colaborador no tiene suficientes puntos para realizar el canje.");
        }
    }
}