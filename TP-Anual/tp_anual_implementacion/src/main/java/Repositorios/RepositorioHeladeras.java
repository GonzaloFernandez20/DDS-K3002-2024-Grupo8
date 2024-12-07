package Repositorios;

import DTOs.ColaboradorHumanoDTO;
import Modelo.Dominio.Persona.PersonaHumana;
import Modelo.Dominio.colaborador.Colaborador;
import Modelo.Dominio.contribucion.Vianda;
import Modelo.Dominio.documentacion.Documento;
import Modelo.Dominio.documentacion.Sexo;
import Modelo.Dominio.documentacion.TipoDeDocumento;
import Modelo.Dominio.heladera.EstadoHeladera;
import Modelo.Dominio.heladera.Heladera;
import Modelo.Dominio.heladera.Modelo;
import Modelo.Dominio.localizacion.Direccion;
import Modelo.Dominio.localizacion.Ubicacion;
import Modelo.Dominio.medios_de_contacto.Mail;
import Modelo.Dominio.Persona.PersonaJuridica;
import Modelo.Dominio.Persona.TipoOrganizacion;
import Modelo.Mappers.ColabHumanoMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioHeladeras {

    private static RepositorioHeladeras instancia;
    private static List<Heladera> heladeras;

    public static RepositorioHeladeras getInstancia(){
        if(instancia == null){
            instancia = new RepositorioHeladeras();
        }
        return instancia;
    }

    public List<Heladera> getHeladeras() {
        verificarExistenciaDeHeladeras();

        System.out.println("1.Cantidad de heladeras: " + heladeras.size());

        Heladera heladera1 = new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50"), "CABA", "Gastronomos Argentinos 1"), 15, new Modelo(15, -2), LocalDate.now());
        heladera1.setIdHeladera(9999);
        Heladera heladera2 = new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50"), "CABA", "Gastronomos Argentinos 2"), 2, new Modelo(15, -2), LocalDate.now());
        heladera2.setIdHeladera(1111);
        Heladera heladera3 = new Heladera(new Colaborador(new PersonaJuridica("Gastronomos Argentinos", TipoOrganizacion.ONG, "GASTRONOMIA", new Direccion("Perú", "50")), List.of(new Mail("gastronomosargentinos@gmail.com"))), new Ubicacion(new Direccion("Perú", "50"), "CABA", "Gastronomos Argentinos 3"), 20, new Modelo(10, -2), LocalDate.now());
        heladera3.setIdHeladera(3030);
        heladera3.setEstado(EstadoHeladera.INACTIVA);

        System.out.println("2.Cantidad de heladeras: " + heladeras.size());

        Mail mail = new Mail();
        mail.setCorreo("alfred200@gmail.com");
        Colaborador colaborador = ColabHumanoMapper.crearColaboradorHumanoAPartirDe(
                new ColaboradorHumanoDTO(
                        "alfredo123",
                        "alref000",
                        "Alfredo",
                        "García",
                        LocalDate.now().minusYears(40),
                        TipoDeDocumento.DNI,
                        "20300444",
                        Sexo.MASCULINO,
                        "Carlos Calvo",
                        "400",
                        "alfredo@gmail.com",
                        "1132567897",
                        true,
                        true
                        )
        ).getColaborador();
        heladera1.recibirVianda(new Vianda("Fideos", LocalDate.now().plusDays(5), colaborador, heladera1, null, null));
        heladera1.recibirVianda(new Vianda("Arroz con Pollo", LocalDate.now().plusDays(5), colaborador, heladera1, null, null));
        heladera1.recibirVianda(new Vianda("Milanesa napolitana", LocalDate.now().plusDays(5), colaborador, heladera1, null, null));

        System.out.println("3.Cantidad de heladeras: " + heladeras.size());

        heladeras.add(heladera1);
        System.out.println("4.Cantidad de heladeras: " + heladeras.size());
        heladeras.add(heladera2);
        System.out.println("5.Cantidad de heladeras: " + heladeras.size());
        heladeras.add(heladera3);

        System.out.println("6.Cantidad de heladeras: " + heladeras.size());

        return heladeras;
    }

    public Heladera buscarHeladeraPorId(int idBuscado) {
        return heladeras.stream().filter(heladera -> heladera.getIdHeladera() == idBuscado).findFirst().orElse(null);
    }

    public void agregarHeladera(Heladera heladera) {
        verificarExistenciaDeHeladeras();
        System.out.println("Agrego heladera " + heladera.getUbicacion().getNombreCompletoDeUbicacion());
        heladeras.add(heladera);
    }

    private void verificarExistenciaDeHeladeras() {
        if(heladeras == null){
            heladeras = new ArrayList<>();
        }
    }
}