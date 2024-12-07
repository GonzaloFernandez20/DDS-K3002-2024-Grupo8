package dominio;

import java.util.List;

import utils.MovimientoAprendido;

public class Pokemon {
    public int id;
    public String name;
    public String image;

    public List <MovimientoAprendido> moves;

    public String getName(){ return name; }
    public int getId(){ return id; }

    public void setImage(String URL){
        this.image = URL;
    }
}
