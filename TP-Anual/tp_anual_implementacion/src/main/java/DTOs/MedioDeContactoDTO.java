package DTOs;

public class MedioDeContactoDTO {
        private Integer id;
        private String tipo; // Ej. "Mail", "WhatsApp"
        private String valor; // Ej. "correo@example.com", "+1234567890"

        public MedioDeContactoDTO(String tipo, String valor) {
            this.tipo = tipo;
            this.valor = valor;
        }

        // Getters y setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}