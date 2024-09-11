package Modelo.carga_masiva;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.persona.PersonaHumana;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.sistema.Sistema;
import Modelo.Dominio.medios_de_contacto.MedioDeContacto;
import Modelo.Dominio.contribucion.DonacionDeDinero;
import Modelo.Dominio.contribucion.DonacionDeVianda;
import Modelo.Dominio.contribucion.DistribucionDeVianda;
import Modelo.Dominio.contribucion.RegistroDePersonasEnSituacionVulnerable;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class CargaMasiva {
    private final String archivo;

    public CargaMasiva(String archivo) {
        if(archivo.endsWith(".csv")) {
            this.archivo = archivo;
        } else throw new RuntimeException("El archivo ingresado no es del tipo correcto.");
    }

    public void migrar() {
        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                for (int i = 0; i < linea.length; i++) {
                    String campo = linea[i];

                    String[] partes = campo.split(";");

                    if(!this.sonCeldasValidas(partes)) {
                        throw new RuntimeException("El CSV contiene celdas que no cumplen con lo estipulado.");
                    }

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

                    System.out.println("Procesando colaborador: " + nombre + " " + apellido + " con documento: " + tipoDocString + " " + doc + " y mail: " + mail);

                    TipoDeDocumento tipoDoc = this.castearTipoDoc(tipoDocString);
                    List<MedioDeContacto> mediosDeContacto = new ArrayList<>();
                    Mail mailMedio = new Mail(mail);
                    mediosDeContacto.add(mailMedio);
                    Documento documento = new Documento(tipoDoc, doc, null);
                    PersonaHumana persona = new PersonaHumana(nombre, apellido, null,documento,null);
                    Colaborador colaborador = new Colaborador(persona,mediosDeContacto);

                    this.agregarContribucionPorTipo(tipoDonacion, colaborador, fechaContribucion, Integer.parseInt(cantidad));
                    Sistema.getInstancia().actualizarPorCargaMasiva(colaborador);

                    if (Sistema.getInstancia().existeColaborador(colaborador)) {
                        System.out.println("Colaborador agregado exitosamente: " + nombre + " " + apellido);
                    } else {
                        System.out.println("Error al agregar colaborador: " + nombre + " " + apellido);
                    }
                }
            }
        } catch(FileNotFoundException e) {
            System.err.println("Error al no encontrar el archivo: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al abrir el archivo: " + e.getMessage());
        } catch (CsvValidationException e) {
            System.err.println("Error de validación CSV: " + e.getMessage());
        }
    }

    public void agregarContribucionPorTipo(String tipoDonacion, Colaborador colaborador, LocalDate fechaContribucion, int cantidad) {
        switch (tipoDonacion) {
            case "DINERO":
                DonacionDeDinero contribucionDinero = new DonacionDeDinero(colaborador, cantidad, null, fechaContribucion);
                colaborador.registrarContribucion(contribucionDinero);
                break;
            case "DONACION_VIANDAS":
                DonacionDeVianda contribucionDonarVianda = new DonacionDeVianda(colaborador, null, null, fechaContribucion);
                colaborador.registrarContribucion(contribucionDonarVianda);
                break;
            case "REDISTRIBUCION_VIANDAS":
                DistribucionDeVianda contribucionDistribuirVianda = new DistribucionDeVianda(colaborador, null, null, null, null, fechaContribucion);
                colaborador.registrarContribucion(contribucionDistribuirVianda);
                break;
            case "ENTREGA_TARJETAS":
                RegistroDePersonasEnSituacionVulnerable contribucionRegistro = new RegistroDePersonasEnSituacionVulnerable(colaborador, null, fechaContribucion);
                colaborador.registrarContribucion(contribucionRegistro);
                break;
        }
    }

    public TipoDeDocumento castearTipoDoc(String tipoDoc) {
        switch (tipoDoc) {
            case "LC":
                return TipoDeDocumento.LC;
            case "LE":
                return TipoDeDocumento.LE;
            case "DNI":
                return TipoDeDocumento.DNI;
            default:
                return null;
        }
    }

    public Boolean sonCeldasValidas(String[] partes) {
        return this.castearTipoDoc(partes[0]) != null &&
                partes[1].length() <= 10 || isNumeric(partes[1]) &&
                partes[2].length() <= 50 &&
                partes[3].length() <= 50 &&
                partes[4].length() <= 50 && EmailValidator.getInstance().isValid(partes[4]) &&
                partes[5].length() <=10 && this.esFechaValida(partes[5]) &&
                partes[6].equals("DINERO") || partes[6].equals("DONACION_VIANDAS") || partes[6].equals("REDISTRIBUCION_VIANDAS") || partes[6].equals("ENTREGA_TARJETAS") &&
                partes[7].length() <= 7 && isNumeric(partes[7]);
    }

    public Boolean esFechaValida(String fecha) {
        try {
            String[] fechaPorPartes = fecha.split("/");
            LocalDate.of(Integer.parseInt(fechaPorPartes[2]), Integer.parseInt(fechaPorPartes[1]), Integer.parseInt(fechaPorPartes[0]));
            return true;
        } catch (ArrayIndexOutOfBoundsException | DateTimeException e) {
            return false;
        }
    }
}

