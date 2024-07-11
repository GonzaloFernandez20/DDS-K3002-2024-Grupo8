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
        // Estaria bueno atajar si el archivo no es .csv o no existe
        this.archivo = archivo;
    }

    public void migrar() {
        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                for (String campo : linea) {
                    System.out.print(campo + " ");

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

                    MedioDeContacto mailMedio = new MedioDeContacto(mail);
                    Documento documento = new Documento(tipoDoc, doc, null);
                    // Tomando como constructor:
                    // PersonaHumana(Direccion direccion, String nombre, String apellido, LocalDate fechaDeNacimiento, Documento documento)
                    //PersonaHumana persona = new PersonaHumana(null, nombre, apellido, null, documento);
                    PersonaHumana persona = new PersonaHumana(nombre, apellido, null,documento,null);
                    // Tomando como constructor:
                    // Colaborador(Persona persona)
                    Colaborador colaborador = new Colaborador(persona);
                    colaborador.agregarMedioDeContacto(mailMedio);
                    if(Sistema.getInstancia().existeColaborador(colaborador)){
                        Colaborador colaboradorExistente = Sistema.getInstancia().buscarColaborador(colaborador);
                        switch (tipoDonacion) {
                            case "DINERO":
                                DonacionDeDinero contribucionDinero = new DonacionDeDinero(colaboradorExistente, fechaContribucion, Integer.parseInt(cantidad), null);
                                colaboradorExistente.sumarContribucion(contribucionDinero);
                                break;
                            case "DONACION_VIANDAS":
                                DonacionDeVianda contribucionDonarVianda = new DonacionDeVianda(colaboradorExistente, fechaContribucion, null, null);
                                colaboradorExistente.sumarContribucion(contribucionDonarVianda);
                                break;
                            case "REDISTRIBUCION_VIANDAS":
                                DistribucionDeVianda contribucionDistribuirVianda = new DistribucionDeVianda(colaboradorExistente, fechaContribucion, null, null, Integer.parseInt(cantidad), null);
                                colaboradorExistente.sumarContribucion(contribucionDistribuirVianda);
                                break;
                            case "ENTREGA_TARJETAS":
                                RegistroDePersonaEnSituacionVulnerable contribucionRegistro = new RegistroDePersonaEnSituacionVulnerable(colaboradorExistente, fechaContribucion, null);
                                colaboradorExistente.sumarContribucion(contribucionRegistro);
                                break;
                        }
                    }else {
                        switch (tipoDonacion) {
                            case "DINERO":
                                DonacionDeDinero contribucionDinero = new DonacionDeDinero(colaborador, fechaContribucion, Integer.parseInt(cantidad), null);
                                colaborador.sumarContribucion(contribucionDinero);
                                break;
                            case "DONACION_VIANDAS":
                                DonacionDeVianda contribucionDonarVianda = new DonacionDeVianda(colaborador, fechaContribucion, null, null);
                                colaborador.sumarContribucion(contribucionDonarVianda);
                                break;
                            case "REDISTRIBUCION_VIANDAS":
                                DistribucionDeVianda contribucionDistribuirVianda = new DistribucionDeVianda(colaborador, fechaContribucion, null, null, Integer.parseInt(cantidad), null);
                                colaborador.sumarContribucion(contribucionDistribuirVianda);
                                break;
                            case "ENTREGA_TARJETAS":
                                RegistroDePersonaEnSituacionVulnerable contribucionRegistro = new RegistroDePersonaEnSituacionVulnerable(colaborador, fechaContribucion, null);
                                colaborador.sumarContribucion(contribucionRegistro);
                                break;
                        }
                    }
                    Sistema.getInstancia().actualizarSegunCargaMasiva(colaborador);
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
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

