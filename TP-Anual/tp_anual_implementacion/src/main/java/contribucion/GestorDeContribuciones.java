package contribucion;

import colaborador.Colaborador;
import nuestras_excepciones.ColaboracionInvalida;

public class GestorDeContribuciones{
    /*     comentario:
                    Este recibe ya instanciada una contribucion y se encarga de efectuar();
                    Se decide que al igual que un colaborador, su instanciacion depende de un form. */
    //estos atributos deberían ser pétreos porque la asociación entre colaborador y contribución no puede cambiar
    private final Contribucion contribucionActual;
    private final Colaborador colaboradorActual;

    //Inyector de dependencias ->constructor
    public GestorDeContribuciones(Contribucion contribucion, Colaborador colaborador){
        this.contribucionActual = contribucion;
        this.colaboradorActual = colaborador;
    }

    private void realizarContribucion(){
        try {
            contribucionActual.procesarContribucion();
            colaboradorActual.sumarContribucion(contribucionActual);
        }
        catch (ColaboracionInvalida e){
            e.printStackTrace();
            System.out.print("CONTRIBUCION ABORTADA");
            System.out.print(contribucionActual);
        }
    }
}
