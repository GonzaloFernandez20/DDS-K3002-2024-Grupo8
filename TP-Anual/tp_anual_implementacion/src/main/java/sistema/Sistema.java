package sistema;

import colaborador.Colaborador;
import colaborador.RegistroAperturaHeladera;
import contribucion.OfertaDeProductos;
import documentacion.Documento;
import heladera.Heladera;
import heladera.TipoAlerta;
import incidentes.Incidente;
import localizacion.Ubicacion;
import medios_de_contacto.MedioDeContacto;
import tecnico.Tecnico;

import java.util.*;

public final class Sistema {
    private static Sistema instancia = null;
    List<Colaborador> colaboradores = new ArrayList<>();;
    List<Heladera> heladeras = new ArrayList<>();
    List<Tecnico> tecnicos = new ArrayList<>();
    List<OfertaDeProductos> ofertas = new ArrayList<>();
    List<Incidente> incidentes = new ArrayList<>();
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
        MedioDeContacto unMail;
        try {
            unMail = colaborador.getMediosDeContacto().getFirst();
        } catch(NoSuchElementException e) {
            unMail = null;
        }
        Documento documento = colaborador.getPersona().getDocumento();

        if(this.existeColaborador(colaborador)) {
            Colaborador colaboradorHallado = this.buscarColaborador(colaborador);
            if(!colaborador.getMediosDeContacto().isEmpty() && !colaboradorHallado.tieneMedioDeContacto(colaborador.getMediosDeContacto().get(0))) {
                colaboradorHallado.agregarMedioDeContacto(unMail);
            }
            if(colaboradorHallado.getPersona().getDocumento() == null) {
                colaboradorHallado.getPersona().setDocumento(documento);
            }
            try {
                colaboradorHallado.agregarContribucionEnLista(colaborador.getContribucionesRealizadas().getFirst());
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

    public void darDeAltaTecnico(Tecnico tecnico) {

        tecnicos.add(tecnico);
        GestorDeTecnicos.getInstancia().darDeAltaTecnico(tecnico);
    }

    public void darDeBajaColaborador(Colaborador colaborador) {
        colaboradores.remove(colaborador);
    }

    public void darDeBajaHeladera(Heladera heladera) {
        heladeras.remove(heladera);
    }

    public void darDeBajaTecnico(Tecnico tecnico) {
        GestorDeTecnicos.getInstancia().darDeBajaTecnico(tecnico);
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
            Optional<Colaborador> colaboradorEncontrado = this.getColaboradores().stream().filter(
                    colaborador -> (this.tieneMedioDeContacto(colaborador, colaboradorBuscado) || colaborador.tieneDocumentoSegunNumeroYTipo(colaboradorBuscado.getDocumento()))).findFirst();

            return colaboradorEncontrado.orElse(null);
    }

    public boolean tieneMedioDeContacto(Colaborador colaborador, Colaborador colaboradorBuscado) {
        return !colaborador.getMediosDeContacto().isEmpty() &&
                !colaboradorBuscado.getMediosDeContacto().isEmpty() &&
                colaborador.tieneMedioDeContacto(colaboradorBuscado.getMediosDeContacto().get(0));
    }

    public void recibirTemperatura(Float temperatura, Heladera heladera) {
        // Lógica para manejar la temperatura recibida
        // que hacemos cuando recibe la info de la heladera???
        System.out.println("Temperatura recibida en el sistema: " + temperatura);
        // Aquí puedes agregar lógica adicional para manejar la temperatura
    }

    public void serAlertado(Heladera heladera, TipoAlerta tipoAlerta) {
        // this.darTecnicoMasCercano() VER UBICACION
    }

    public Tecnico darTecnicoMasCercano(Ubicacion ubicacion) {
        return GestorDeTecnicos.getInstancia().darTecnicoMasCercano(ubicacion);
    }

    public void sumarIncidente(Incidente incidente){
        GestorDeIncidentes gestorDeIncidentes = GestorDeIncidentes.getInstancia();
        gestorDeIncidentes.recibirReporte(incidente);
        incidentes.add(incidente);
    }

    public void registrarMovimiento(RegistroAperturaHeladera registro) {
        Heladera heladera = registro.getHeladera();
        heladera.permitirAcceso(registro);
    }

    public List<Heladera> recomendarHeladeras(Colaborador colaborador) {
        // ver como hacerlo
        return null;
    }
}
