package sistema;

import colaborador.Colaborador;
import contribucion.OfertaDeProductos;
import documentacion.Documento;
import heladera.Heladera;
import medios_de_contacto.MedioDeContacto;
import tecnico.Tecnico;

import java.util.List;
import java.util.Objects;

public final class Sistema {
    private static Sistema instancia = null;
    List<Colaborador> colaboradores;
    List<Heladera> heladeras;
    List<Tecnico> tecnicos;
    List<OfertaDeProductos> ofertas;
    static int contadorDeVinculaciones = 0;
    float monto;

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public int getContadorDeVinculaciones() {
        return contadorDeVinculaciones;
    }

    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public void actualizarSegunCargaMasiva(Colaborador colaborador) {
        MedioDeContacto unMail = colaborador.getMediosDeContacto().get(0);
        Documento documento = colaborador.getPersona().getDocumento();
        Boolean estaEnElSistema = false;

        // Estaria bueno tener una lista de personas, en caso que ya exista la PersonaHumana del Colaborador nuevo
        // Por ejemplo: Tenemos dentro del sistema un tecnico que antes de la carga masiva era colaborador,
        // por lo que ya existe en el sistema
        for (int i = 0; i < colaboradores.size(); i++) {
            if (colaboradores.get(i).tieneMail(unMail) || colaboradores.get(i).tieneDocumentoSegunNumeroYTipo(documento)) {
                colaboradores.get(i).actualizarConCargaMasiva(colaborador);
                estaEnElSistema = true;
            }
        }

        if (!estaEnElSistema) {
            colaboradores.add(colaborador);
        }
    }

    public void darDeAltaColaborador(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

    public void darDeAltaHeladera(Heladera heladera) {
        heladeras.add(heladera);
    }

    public void darDeAltaTecnico(Tecnico tecnico) {
        tecnicos.add(tecnico);
    }

    public void darDeBajaColaborador(Colaborador colaborador) {
        colaboradores.remove(colaborador);
    }

    public void darDeBajaHeladera(Heladera heladera) {
        heladeras.remove(heladera);
    }

    public void darDeBajaTecnico(Tecnico tecnico) {
        tecnicos.remove(tecnico);
    }

    public void agregarOferta(OfertaDeProductos oferta) {
        ofertas.add(oferta);
    }

    public void agregarMonto(float monto) {
        this.monto += monto;
    }

    public boolean existeColaborador(Colaborador colaborador) {
        return Objects.nonNull(this.buscarColaborador(colaborador));
    }

    public Colaborador buscarColaborador(Colaborador colaborador) {
        //return colaboradores.stream().findFirst(unColaborador ->unColaborador.tieneMail(colaborador.getMediosDeContacto().get(0)) || unColaborador.tieneDocumentoSegunNumeroYTipo(colaborador.getDocumento()));
        Colaborador colaboradorHallado = null;
        for (int i = 0; i < colaboradores.size(); i++) {
            if (colaboradores.get(i).tieneMail(colaborador.getMediosDeContacto().get(0)) ||
                    colaboradores.get(i).tieneDocumentoSegunNumeroYTipo(colaborador.getDocumento())) {
                colaboradorHallado =  colaboradores.get(i);
            }
        }
        return colaboradorHallado;
    }
}
