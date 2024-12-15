package Modelo.carga_masiva;

import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.*;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.*;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Entity
@Table(name ="carga_masiva")
public class ColaboracionesCSV {
    @Id
    @GeneratedValue
    private Integer id_colaboracionesCSV;
    @Column
    private String archivo;

    public ColaboracionesCSV(String pathArchivo) {
        this.archivo = pathArchivo;
    }

    public ColaboracionesCSV() {

    }

    public List<Colaborador> obtenerColaboradores() {
        List<Colaborador> colaboradores = new ArrayList<>();

        String folderPath = new File("src/main/resources/static").getAbsolutePath();
        String filePath = folderPath + "/" + archivo;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
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

                    TipoDeDocumento tipoDoc = this.castearTipoDocumento(tipoDocString);

                    Colaborador colaborador = GestorCargaMasiva.obtenerColaboradorSegunPresencia(nombre, apellido, tipoDoc, doc, mail);

                    this.agregarContribucionPorTipo(tipoDonacion, colaborador, fechaContribucion, Integer.parseInt(cantidad));
                    colaboradores.add(colaborador);
                }
            }
        } catch(FileNotFoundException e) {
            System.err.println("Error al no encontrar el archivo: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al abrir el archivo: " + e.getMessage());
        } catch (CsvValidationException e) {
            System.err.println("Error de validación CSV: " + e.getMessage());
        }

        return colaboradores;
    }

    public void agregarContribucionPorTipo(String tipoDonacion, Colaborador colaborador, LocalDate fechaContribucion, int cantidad) {
        switch (tipoDonacion) {
            case "DINERO":
                DonacionDeDinero contribucionDinero = new DonacionDeDinero(colaborador, cantidad, null, fechaContribucion);
                colaborador.registrarContribucion(contribucionDinero);
                break;
            case "DONACION_VIANDAS":
                List<Vianda> viandasNulas = new ArrayList<>();
                for(int i = 0; i<cantidad; i++) {
                    Vianda vianda = new Vianda();
                    vianda.setColaborador(colaborador);
                    vianda.setFechaDeDonacion(fechaContribucion);
                    viandasNulas.add(vianda);
                }
                DonacionDeViandas contribucionDonarVianda = new DonacionDeViandas(colaborador, null, viandasNulas, fechaContribucion);
                colaborador.registrarContribucion(contribucionDonarVianda);
                break;
            case "REDISTRIBUCION_VIANDAS":
                DistribucionDeViandas contribucionDistribuirVianda = new DistribucionDeViandas(colaborador, null, null, null, cantidad, fechaContribucion);
                colaborador.registrarContribucion(contribucionDistribuirVianda);
                break;
            case "ENTREGA_TARJETAS":
                for(int i=0; i<cantidad; i++) {
                    RegistroDePersonaVulnerable contribucionRegistro = new RegistroDePersonaVulnerable(colaborador, null, fechaContribucion);
                    colaborador.registrarContribucion(contribucionRegistro);
                }
                break;
        }
    }

    public TipoDeDocumento castearTipoDocumento(String tipoDoc) {
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
        return this.castearTipoDocumento(partes[0]) != null &&
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

    public String getArchivo() { return archivo; }
}
