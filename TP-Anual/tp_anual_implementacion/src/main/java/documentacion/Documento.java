package documentacion;

public class Documento{
    TipoDeDocumento tipo;
    int numero;
    Sexo sexo;

    public Documento(TipoDeDocumento tipo, int numero, Sexo sexo){
        this.setNumero(numero);
        this.setSexo(sexo);
        this.setTipo(tipo);
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setTipo(TipoDeDocumento tipo) {
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public TipoDeDocumento getTipo() {
        return tipo;
    }
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