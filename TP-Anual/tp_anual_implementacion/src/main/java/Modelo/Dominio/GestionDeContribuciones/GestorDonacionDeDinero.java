package Modelo.Dominio.GestionDeContribuciones;


import Modelo.Dominio.contribucion.DonacionDeDinero;

public class GestorDonacionDeDinero {
    public static void crearContribucion(DonacionDeDinero donacionDeDinero){
        donacionDeDinero.procesarLaContribucion();
        // NEXT: MENSAJE RELACIONADO CON LA PERSISTENCIA
    }
}
