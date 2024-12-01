package Modelo.Dominio.documentacion;

import jakarta.persistence.*;

@Entity
@Table(name = "Documento")
public class Documento {
    @Id
    @GeneratedValue
    private Integer id_documento;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_documento")
    TipoDeDocumento tipo;
    @Column(name = "numero")
    String numero;
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    Sexo sexo;

    //Constructores -------------------------------------------------------------------------------------------------
    public Documento(TipoDeDocumento tipo, String numero, Sexo sexo){
        this.setNumero(numero);
        this.setSexo(sexo);
        this.setTipo(tipo);
    }

    public Documento() {

    }

    // TODO: Revisar porque ahora hay BD y se usa auxiliarmente para test
    public Boolean esDocumentoSegunNumeroYTipo(Documento documento){
        if(this.contieneNumeroYTipo() && documento != null && documento.contieneNumeroYTipo()) {
            return this.getTipo().equals(documento.getTipo()) && this.getNumero().equals(documento.getNumero());
        } else {
            return false;
        }
    }

    // TODO: Revisar porque ahora hay BD y se usa auxiliarmente para test
    public Boolean contieneNumeroYTipo() {
        return this.getTipo() != null && this.getNumero() != null;
    }


    // Getters y Setters ----------------------------------------------------------------------------------------------
    public void setNumero(String numero) { this.numero = numero; }
    public String getNumero() { return numero; }

    public void setSexo(Sexo sexo) { this.sexo = sexo; }
    public Sexo getSexo() { return sexo; }

    public void setTipo(TipoDeDocumento tipo) { this.tipo = tipo; }
    public TipoDeDocumento getTipo() { return tipo;}
}

