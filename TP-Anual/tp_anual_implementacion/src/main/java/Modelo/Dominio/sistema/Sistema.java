package Modelo.Dominio.sistema;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.OfertaDeProductos;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.incidentes.Incidente;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.tecnico.Tecnico;

import java.util.*;

public final class Sistema {
    private static Sistema instancia = null;
    private List<Colaborador> colaboradores = new ArrayList<>();
    private List<Heladera> heladeras = new ArrayList<>();
    private List<OfertaDeProductos> ofertas = new ArrayList<>();
    private List<Incidente> incidentes = new ArrayList<>();

    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public void actualizarPorCargaMasiva(Colaborador colaborador) {
        MedioDeContacto unMail;
        try {
            unMail = colaborador.getMediosDeContacto().getFirst();
        } catch(NoSuchElementException e) {
            unMail = null;
        }
        //Documento documento = colaborador.getPersona().getDocumento();

        if(this.existeColaborador(colaborador)) {
            Colaborador colaboradorHallado = this.buscarColaborador(colaborador);
            if(!colaborador.getMediosDeContacto().isEmpty() && !colaboradorHallado.tieneMedioDeContacto(colaborador.getMediosDeContacto().getFirst())) {
                colaboradorHallado.agregarMedioDeContacto(unMail);
            }
/*            if(colaboradorHallado.getPersona().getDocumento() == null) {
                colaboradorHallado.getPersona().setDocumento(documento);
            }*/
            try {
                //colaboradorHallado.agregarContribucionEnLista(colaborador.getContribucionesRealizadas().getFirst());
            } catch (NoSuchElementException e) {
                System.err.println("No tiene contribuciones hechas previamente.");
            }
        } else {
            this.darDeAltaColaborador(colaborador);
        }
    }

    public void darDeAltaColaborador(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

    public void darDeAltaHeladera(Heladera heladera) {
        heladeras.add(heladera);

    }


    public void darDeBajaColaborador(Colaborador colaborador) {
        colaboradores.remove(colaborador);
    }

    public void darDeBajaHeladera(Heladera heladera) {
        heladeras.remove(heladera);
    }

    public void agregarOferta(OfertaDeProductos oferta) {
        ofertas.add(oferta);
    }

    public boolean existeColaborador(Colaborador colaborador) {
        return this.buscarColaborador(colaborador) != null;
    }

    public Colaborador buscarColaborador(Colaborador colaboradorBuscado) {
            //Optional<Colaborador> colaboradorEncontrado = this.getColaboradores().stream().filter(
                    //colaborador -> (this.tieneMedioDeContacto(colaborador, colaboradorBuscado) || colaborador.tieneDocumentoSegunNumeroYTipo(colaboradorBuscado.getDocumento()))).findFirst();

            //return colaboradorEncontrado.orElse(null);
        return null;
    }

    public boolean tieneMedioDeContacto(Colaborador colaborador, Colaborador colaboradorBuscado) {
        return !colaborador.getMediosDeContacto().isEmpty() &&
                !colaboradorBuscado.getMediosDeContacto().isEmpty() &&
                colaborador.tieneMedioDeContacto(colaboradorBuscado.getMediosDeContacto().getFirst());
    }


    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }
    public List<OfertaDeProductos> getOfertas() { return ofertas; }

}
