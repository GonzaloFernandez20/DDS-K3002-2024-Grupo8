package sistema;

import colaborador.Colaborador;
import contribucion.OfertaDeProductos;
import heladera.Heladera;
import tecnico.Tecnico;

import java.util.List;

public final class Sistema {
    private static Sistema instancia = null;
    List<Colaborador> colaboradores;
    List<Heladera> heladeras;
    List<Tecnico> tecnicos;
    List<OfertaDeProductos> ofertas;
    static int contadorDeVinculaciones = 0;
    float monto;

    public List<Colaborador> getColaboradores() { return colaboradores; }

    public int getContadorDeVinculaciones() { return contadorDeVinculaciones; }

    public static Sistema getInstancia() {
        if(instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public void darDeAltaColaborador(Colaborador colaborador) { colaboradores.add(colaborador); }

    public void darDeAltaHeladera(Heladera heladera) { heladeras.add(heladera); }

    public void darDeAltaTecnico(Tecnico tecnico) { tecnicos.add(tecnico); }

    public void darDeBajaColaborador(Colaborador colaborador) { colaboradores.remove(colaborador); }

    public void darDeBajaHeladera(Heladera heladera) { heladeras.remove(heladera); }

    public void darDeBajaTecnico(Tecnico tecnico) { tecnicos.remove(tecnico); }

    public void agregarOferta(OfertaDeProductos oferta) { ofertas.add(oferta); }

    public void agregarMonto(float monto) { this.monto += monto; }
}
