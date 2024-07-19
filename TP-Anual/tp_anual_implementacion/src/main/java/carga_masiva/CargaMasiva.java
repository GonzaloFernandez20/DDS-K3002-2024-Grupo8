package carga_masiva;

import colaborador.Colaborador;
import org.jetbrains.annotations.NotNull;
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
import contribucion.RegistroDePersonasEnSituacionVulnerable;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import org.apache.commons.validator.routines.EmailValidator;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static documentacion.TipoDeDocumento.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class CargaMasiva {
    private String archivo;

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

                    TipoDeDocumento tipoDoc = this.castearTipoDoc(tipoDocString);
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
            System.err.println("Error al abrir el archivo: " + e.getMessage());
        } catch (CsvValidationException e) {
            System.err.println("Error de validación CSV: " + e.getMessage());
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
                RegistroDePersonasEnSituacionVulnerable contribucionRegistro = new RegistroDePersonasEnSituacionVulnerable(colaborador, fechaContribucion, null);
                colaborador.sumarContribucion(contribucionRegistro);
                break;
        }
    }

    public TipoDeDocumento castearTipoDoc(@NotNull String tipoDoc) {
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

    public Boolean sonCeldasValidas(String[] partes) {
        return this.castearTipoDoc(partes[0]) != null &&
                partes[1].length() <= 10 || isNumeric(partes[1]) &&
                partes[2].length() <= 50 &&
                partes[3].length() <= 50 &&
                partes[4].length() <= 50 && EmailValidator.getInstance().isValid(partes[4]) &&
                partes[5].length() <=10 && this.esFechaValidaConFormato(partes[5], "dd/mm/yyyy") &&
                partes[6].equals("DINERO") || partes[6].equals("DONACION_VIANDAS") || partes[6].equals("REDISTRIBUCION_VIANDAS") || partes[6].equals("ENTREGA_TARJETAS") &&
                partes[7].length() <= 7 && isNumeric(partes[7]);
    }

    public Boolean esFechaValidaConFormato(String fecha, String formato) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(formato);
        try {
            LocalDate.parse(fecha, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

