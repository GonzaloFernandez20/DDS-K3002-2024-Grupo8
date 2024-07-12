package documentacion;

public class Documento{
    TipoDeDocumento tipo;
    String numero;
    Sexo sexo;

    public Documento(TipoDeDocumento tipo, String numero, Sexo sexo){
        this.setNumero(numero);
        this.setSexo(sexo);
        this.setTipo(tipo);
    }

    public void setNumero(String numero) { this.numero = numero; }
    public String getNumero() { return numero; }

    public void setSexo(Sexo sexo) { this.sexo = sexo; }
    public Sexo getSexo() { return sexo; }

    public void setTipo(TipoDeDocumento tipo) { this.tipo = tipo; }
    public TipoDeDocumento getTipo() { return tipo;}

    public Boolean esDocumentoSegunNumeroYTipo(Documento documento){
        return documento != null && this.getTipo().equals(documento.getTipo()) && this.getNumero().equals(documento.getNumero());
    }
}