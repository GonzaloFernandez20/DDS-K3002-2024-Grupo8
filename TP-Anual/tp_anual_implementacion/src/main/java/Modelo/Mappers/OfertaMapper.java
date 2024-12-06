package Modelo.Mappers;

import Controladores.DescargaDeArchivo;
import DTOs.OfertaDeUnProductoDTO;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeUnProducto;
import Modelo.Dominio.contribucion.Producto;
import Modelo.Dominio.contribucion.Rubro;

public class OfertaMapper {

    public static OfertaDeUnProducto crearOfertaAPartirDe(Colaborador colaborador, OfertaDeUnProductoDTO ofertaDTO){
        Producto producto = new Producto(ofertaDTO.getNombreProducto(), ofertaDTO.getStock());

        OfertaDeUnProducto nuevaOferta = new OfertaDeUnProducto(
                colaborador,
                ofertaDTO.getNombreOferta(),
                ofertaDTO.getPuntosNecesarios(),
                ofertaDTO.getLinkDeImagen(),
                Rubro.valueOf(ofertaDTO.getRubro()),
                producto
        );

        return nuevaOferta;
    }
}
