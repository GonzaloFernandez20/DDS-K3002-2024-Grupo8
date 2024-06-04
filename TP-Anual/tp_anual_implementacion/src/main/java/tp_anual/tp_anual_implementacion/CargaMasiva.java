package tp_anual.tp_anual_implementacion;

import tp_anual.tp_anual_implementacion.Contribucion;
import tp_anual.tp_anual_implementacion.Colaborador;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CargaMasiva {
    List<Colaborador> colaboradoresCargados;

    public void migrar() {
        String archivo = getClass().getClassLoader().getResource("CSV.csv").getPath();
        //El archivo debe estar en la carpeta resources
        try (CSVReader reader = new CSVReader(new FileReader(archivo))) {
            String[] linea;
            while ((linea = reader.readNext()) != null) {
                for (String campo : linea) {
                    System.out.print(campo + " ");

                    String[] partes = campo.split(";");

                    String nombre = partes[2];
                    String apellido = partes[3];
                    String mail = partes[4];
                    String fecha = partes[5];
                    //Hay que pasar a tipo de dato Date
                    String tipoDonacion = partes[6];
                    String cantidad = partes[7];

                    PersonaHumana colaborador = new PersonaHumana(nombre, apellido, null, null);
                    MedioDeContacto medioMail = new CorreoElectronico(mail);
                    colaborador.agregarMedioDeContacto(medioMail);

                    switch (tipoDonacion) {
                        case "DINERO":
                            DonacionDeDinero contribucionDinero = new DonacionDeDinero(colaborador, fecha, cantidad, null);
                            colaborador.sumarContribucion(contribucionDinero);
                            break;
                        case "DONACION_VIANDAS":
                            DonacionDeVianda contribucionDonarVianda = new DonacionDeVianda(colaborador, fecha, null, null);
                            colaborador.sumarContribucion(contribucionDonarVianda);
                            break;
                        case "REDISTRIBUCION_VIANDAS":
                            DistribucionDeVianda contribucionDistribuirVianda = new DistribucionDeVianda(colaborador, fecha, null, null, cantidad, null);
                            colaborador.sumarContribucion(contribucionDistribuirVianda);
                            break;
                        case "ENTREGA_TARJETAS":
                            RegistroDePersonasEnSituacionVulnerable contribucionRegistro = new RegistroDePersonasEnSituacionVulnerable(colaborador, fecha, null);
                            colaborador.sumarContribucion(contribucionRegistro);
                            break;
                    }

                    if(!this.corroborarColaboradorYaCargadoSegunMail(mail)) {
                        colaboradoresCargados.add(colaborador);
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean corroborarColaboradorYaCargadoSegunMail(String mail) {
        return colaboradoresCargados.stream().anyMatch(
                colaborador -> colaborador.getMediosDeContacto().stream().anyMatch(medio -> medio.getIdentificacion().equals(mail))
        );
    }
}

