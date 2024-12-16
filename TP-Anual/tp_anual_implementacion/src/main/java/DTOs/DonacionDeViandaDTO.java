package DTOs;

import java.util.Date;
import java.util.List;

public class DonacionDeViandaDTO {
   private int heladeraID;
   private List<ViandaDTO> viandasDTO;

    // Constructor ----------------------------------------------------
    public DonacionDeViandaDTO() {
    }

    // Getters y setters -----------------------------------------------------------------
    public int getHeladeraID() {
        return heladeraID;
    }
    public void setHeladeraID(int heladera) {
        this.heladeraID = heladera;
    }

    public List<ViandaDTO> getViandasDTO() {
        return viandasDTO;
    }
    public void setViandasDTO(List<ViandaDTO> viandasDTO) {
        this.viandasDTO = viandasDTO;
    }
}
