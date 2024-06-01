package TPAnual;

public class PersonaSituacionVulnerable {
    String nombre;
    Fecha fechaDeNacimiento;
    EstadoDeVivienda estadoDeVivienda;
    Direccion domicilio;
    Documento documento;
    int cantMenores;
    Vinculacion vinculacion;
}

enum EstadoDeVivienda {
    poseeDomicilio,
    situacionDeCalle
}

class Vinculacion {
    PersonaHumana colaboradorQueRegistro;
    PersonaSituacionVulnerable personaSituacionVulnerable;
    Fecha fechaRegistro;
    AccesoAHeladeras tarjetaEntregada;
}

class AccesoAHeladeras {
    PersonaSituacionVulnerable personaSituacionVulnerable;
    String id;
    int cantUsosRestantesPorDia;
    UsoXTarjeta usos[];
}

class UsoXTarjeta {
    Heladera heladera;
    Vianda vianda;
    Fecha fecha;
}

class Documento{
    TipoDeDocumento tipo;
    int numero;
    Sexo sexo;
}

enum TipoDeDocumento{
    LC,
    LE,
    CI,
    DNI,
    PASAPORTE
}

enum Sexo {
    FEMENINO,
    MASCULINO,
    OTRA
}