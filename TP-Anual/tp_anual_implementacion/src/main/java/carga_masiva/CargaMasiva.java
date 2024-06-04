package carga_masiva;

import colaborador.Colaborador;
import colaborador.PersonaHumana;
import sistema.Sistema;
import medios_de_contacto.MedioDeContacto;
import medios_de_contacto.CorreoElectronico;
import contribucion.DonacionDeDinero;
import contribucion.DonacionDeVianda;
import contribucion.DistribucionDeVianda;
import contribucion.RegistroDePersonasEnSituacionVulnerable;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.time.LocalDate;

public class CargaMasiva {
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
                    String[] fechaPorPartes = fecha.split("/");
                    LocalDate fechaContribucion = LocalDate.of(Integer.parseInt(fechaPorPartes[2]), Integer.parseInt(fechaPorPartes[1]), Integer.parseInt(fechaPorPartes[0]));
                    String tipoDonacion = partes[6];
                    String cantidad = partes[7];

                    PersonaHumana colaborador = new PersonaHumana(nombre, apellido, null, null);
                    MedioDeContacto medioMail = new CorreoElectronico(mail);
                    colaborador.agregarMedioDeContacto(medioMail);

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
                            RegistroDePersonasEnSituacionVulnerable contribucionRegistro = new RegistroDePersonasEnSituacionVulnerable(colaborador, fechaContribucion, null);
                            colaborador.sumarContribucion(contribucionRegistro);
                            break;
                    }

                    if(!this.corroborarColaboradorYaCargadoSegunMail(mail)) {
                        List<Colaborador> colaboradoresCargados = Sistema.getInstancia().getColaboradores();
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
        List<Colaborador> colaboradoresCargados = Sistema.getInstancia().getColaboradores();
        return colaboradoresCargados.stream().anyMatch(
                colaborador -> colaborador.getMediosDeContacto().stream().anyMatch(medio -> medio.getIdentificacion().equals(mail))
        );
    }
}

