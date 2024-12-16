package DAOs;

public class ViandasPorHeladeraDAO {
    private Integer id_heladera;
    private Integer ViandasRetiradas;
    private Integer ViandasColocadas;

    public ViandasPorHeladeraDAO() {
    }

    public ViandasPorHeladeraDAO(Integer id_heladera, Integer viandasRetiradas, Integer viandasColocadas) {
        this.id_heladera = id_heladera;
        ViandasRetiradas = viandasRetiradas;
        ViandasColocadas = viandasColocadas;
    }

    public Integer getId_heladera() {
        return id_heladera;
    }

    public void setId_heladera(Integer id_heladera) {
        this.id_heladera = id_heladera;
    }

    public Integer getViandasRetiradas() {
        return ViandasRetiradas;
    }

    public void setViandasRetiradas(Integer viandasRetiradas) {
        ViandasRetiradas = viandasRetiradas;
    }

    public Integer getViandasColocadas() {
        return ViandasColocadas;
    }

    public void setViandasColocadas(Integer viandasColocadas) {
        ViandasColocadas = viandasColocadas;
    }
}
