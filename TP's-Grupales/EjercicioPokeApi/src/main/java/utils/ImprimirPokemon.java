package utils;


import dominio.Pokemon;

public class ImprimirPokemon{

    public static void imprimirPokemon(Pokemon pokemon){
        if (pokemon != null) {

            final String urlImagen = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getId() + ".png";
            pokemon.setImage(urlImagen);

            System.out.println();
            System.out.println("Nombre: " + pokemon.getName());
            System.out.println("Imagen: " + urlImagen);
            
            if (pokemon.moves.size() > 0) {
                System.out.println("Lista de movimientos de " + pokemon.getName() + ":");
            
                for (MovimientoAprendido unMovimiento : pokemon.moves) {
                    System.out.println(" - " + unMovimiento.conseguirName());
                }

                System.out.println();
            } else {
                System.out.println("El Pokémon " + pokemon.getName() + " no tiene movimientos.");
            }
        } else {
            System.out.println("No se encontró el Pokémon.");
        }
    }
}