package carga_masiva;

import colaborador.Colaborador;
import persona.PersonaHumana;
import documentacion.Documento;
import documentacion.TipoDeDocumento;
import localizacion.Direccion;
import sistema.Sistema;
import medios_de_contacto.MedioDeContacto;
import medios_de_contacto.CorreoElectronico;
import contribucion.DonacionDeDinero;
import contribucion.DonacionDeVianda;
import contribucion.DistribucionDeVianda;
import contribucion.RegistroDePersonaEnSituacionVulnerable;
import contribucion.RegistroDeMultiplesPersonasEnSituacionVulnerable;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import static documentacion.TipoDeDocumento.*;

public class CargaMasiva {
    private String archivo;

    public CargaMasiva(String archivo) {
        this.archivo = archivo;
    }

    public void migrar() {
        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                for (int i = 1; i < linea.length; i++) {
                    String campo = linea[i];

                    String[] partes = campo.split(";");

                    String tipoDocString = partes[0];
                    String doc = partes[1];
                    String nombre = partes[2];
                    String apellido = partes[3];
                    String mail = partes[4];
                    String fecha = partes[5];
                    String[] fechaPorPartes = fecha.split("/");
                    LocalDate fechaContribucion = LocalDate.of(Integer.parseInt(fechaPorPartes[2]), Integer.parseInt(fechaPorPartes[1]), Integer.parseInt(fechaPorPartes[0]));
                    String tipoDonacion = partes[6];
                    String cantidad = partes[7];

                    TipoDeDocumento tipoDoc = this.castearTipoDoc(tipoDocString);
// hay que ver que los datos que vienen del archivo cumplan ciertas condiciones y q no haya atributos nulos
                    MedioDeContacto mailMedio = new MedioDeContacto(mail);
                    Documento documento = new Documento(tipoDoc, doc, null);
                    PersonaHumana persona = new PersonaHumana(nombre, apellido, null,documento,null);
                    Colaborador colaborador = new Colaborador(persona);
                    colaborador.agregarMedioDeContacto(mailMedio);

                    if(Sistema.getInstancia().existeColaborador(colaborador)){
                        Colaborador colaboradorExistente = Sistema.getInstancia().buscarColaborador(colaborador);
                        agregarContribucionPorTipo(tipoDonacion, colaboradorExistente, fechaContribucion, Integer.parseInt(cantidad));
                        Sistema.getInstancia().actualizarPorCargaMasiva(colaboradorExistente);
                    }else {
                        Sistema.getInstancia().darDeAltaColaborador(colaborador);
                        agregarContribucionPorTipo(tipoDonacion, colaborador, fechaContribucion, Integer.parseInt(cantidad));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void agregarContribucionPorTipo(String tipoDonacion, Colaborador colaborador, LocalDate fechaContribucion, int cantidad) {
        switch (tipoDonacion) {
            case "DINERO":
                DonacionDeDinero contribucionDinero = new DonacionDeDinero(colaborador, fechaContribucion, cantidad, null);
                colaborador.sumarContribucion(contribucionDinero);
                break;
            case "DONACION_VIANDAS":
                DonacionDeVianda contribucionDonarVianda = new DonacionDeVianda(colaborador, fechaContribucion, null, null);
                colaborador.sumarContribucion(contribucionDonarVianda);
                break;
            case "REDISTRIBUCION_VIANDAS":
                DistribucionDeVianda contribucionDistribuirVianda = new DistribucionDeVianda(colaborador, fechaContribucion, null, null, cantidad, null);
                colaborador.sumarContribucion(contribucionDistribuirVianda);
                break;
            case "ENTREGA_TARJETAS":
                RegistroDePersonaEnSituacionVulnerable contribucionRegistro = new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, null);
                colaborador.sumarContribucion(contribucionRegistro);
                break;
        }
    }

    public TipoDeDocumento castearTipoDoc(String tipoDoc) {
        switch (tipoDoc) {
            case "LC":
                return LC;
            case "LE":
                return LE;
            case "DNI":
                return DNI;
            default:
                return null;
        }
    }
}

