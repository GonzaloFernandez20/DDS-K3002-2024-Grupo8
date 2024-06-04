package tp_anual.tp_anual_implementacion;

import java.util.List;

public class Sistema {
    List<Colaborador> colaboradores;
    List<Heladera> heladeras;
    List<Tecnico> tecnicos;
    List<OfertaDeProductos> ofertas;

    public void darDeAltaColaborador(Colaborador colaborador) { colaboradores.add(colaborador); }

    public void darDeAltaHeladera(Heladera heladera) { heladeras.add(heladera); }

    public void darDeAltaTecnico(Tecnico tecnico) { tecnicos.add(tecnico); }

    public void darDeBajaColaborador(Colaborador colaborador) { colaboradores.remove(colaborador); }

    public void darDeBajaHeladera(Heladera heladera) { heladeras.remove(heladera); }

    public void darDeBajaTecnico(Tecnico tecnico) { tecnicos.remove(tecnico); }

    public List<PuntoEnElMapa> recomendarPuntosDeColocacionDeHeladeras(PuntoEnElMapa punto, int radio) {
        // Usar la API
    }

    public void agregarOferta(OfertaDeProductos oferta) { ofertas.add(oferta); }
}
