package sistema;

import localizacion.Area;
import localizacion.Ubicacion;
import tecnico.Tecnico;

import java.util.ArrayList;
import java.util.List;

public class GestorDeTecnicos {
    public List<Tecnico> tecnicos = new ArrayList<>();
    private static GestorDeTecnicos instancia = null;
    public static GestorDeTecnicos getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeTecnicos();
        }
        return instancia;
    }
    public Tecnico darTecnicoMasCercano(Ubicacion ubicacion) {
        Tecnico tecnicoHallado = tecnicos.stream().filter(tecnico -> tecnico.estaCercaDe(ubicacion)).toList().get(0);
        if(tecnicoHallado == null){
            //vamos agrandando el radio hasta que encontramos a alguien
            while(tecnicoHallado == null){
                Integer radio = 0;
                Area areaAux = new Area(ubicacion,radio);
                tecnicoHallado = tecnicos.stream().filter(tecnico -> tecnico.coincideConElArea(areaAux)).toList().get(0);
                radio++;
            }
        }
        return tecnicoHallado;
    }
    public void darDeAltaTecnico(Tecnico tecnico) {
        tecnicos.add(tecnico);
    }
    public void darDeBajaTecnico(Tecnico tecnico) {
        tecnicos.remove(tecnico);
    }
}
