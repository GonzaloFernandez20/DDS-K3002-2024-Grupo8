package sistema;

import colaborador.Colaborador;
import contribucion.OfertaDeProductos;
import documentacion.Documento;
import heladera.Heladera;
import medios_de_contacto.MedioDeContacto;
import tecnico.Tecnico;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Sistema {
    private static Sistema instancia = null;
    List<Colaborador> colaboradores = new ArrayList<>();;
    List<Heladera> heladeras = new ArrayList<>();
    List<Tecnico> tecnicos = new ArrayList<>();
    List<OfertaDeProductos> ofertas = new ArrayList<>();
    static int contadorDeVinculaciones = 0;
    float monto;

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public int getContadorDeVinculaciones() {
        return contadorDeVinculaciones;
    }

    public float getMonto() { return monto; }

    public List<OfertaDeProductos> getOfertas() { return ofertas; }
    
    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public void actualizarPorCargaMasiva(Colaborador colaborador) {
        MedioDeContacto unMail = colaborador.getMediosDeContacto().get(0);
        Documento documento = colaborador.getPersona().getDocumento();

        Boolean estaEnElSistema = false;

        if(this.existeColaborador(colaborador)) {
            Colaborador colaboradorHallado = this.buscarColaborador(colaborador);
            if(!colaborador.getMediosDeContacto().isEmpty() && !colaboradorHallado.tieneMedioDeContacto(colaborador.getMediosDeContacto().get(0))) {
                colaboradorHallado.setDocumento(documento);
                colaboradorHallado.agregarMedioDeContacto(unMail);
            }
            if(colaboradorHallado.getPersona().getDocumento() == null) {
                colaboradorHallado.getPersona().setDocumento(colaborador.getPersona().getDocumento());
            }
        } else {
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
        return this.buscarColaborador(colaborador) != null;
    }

    public Colaborador buscarColaborador(Colaborador colaboradorBuscado) {
        for (int i = 0; i < colaboradores.size(); i++) {
            if(colaboradores.get(i).getDocumento() != null && colaboradorBuscado.tieneDocumentoSegunNumeroYTipo(colaboradores.get(i).getDocumento())) {
                return colaboradores.get(i);
            }

            if(!colaboradores.get(i).getMediosDeContacto().isEmpty() && colaboradorBuscado.tieneMedioDeContacto(colaboradores.get(i).getMediosDeContacto().get(0))) {
                return colaboradores.get(i);
            }
        }

        return null;
    }

    public void recibirTemperatura(Float temperatura, Heladera heladera) {
        // Lógica para manejar la temperatura recibida
        // que hacemos cuando recibe la info de la heladera???
        System.out.println("Temperatura recibida en el sistema: " + temperatura);
        // Aquí puedes agregar lógica adicional para manejar la temperatura
    }

    public void serAlertado(Heladera heladera, TipoAlerta tipoAlerta) {
        // el sistema deberá dar aviso al técnico correspondiente (es decir al que se encuentre más cercano a
        //la heladera en cuestión)
    }
}
