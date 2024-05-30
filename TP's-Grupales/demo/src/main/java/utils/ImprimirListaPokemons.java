package utils;

import dominio.Move;
import dominio.Pokemon;

public class ImprimirListaPokemons {
    public static void imprimirPokemonesPorMovimiento(Move listadoPokemones) {

        System.out.println("Pokemones que lo utilizan: ");

        if (listadoPokemones != null && listadoPokemones.learned_by_pokemon != null) {

            for (Pokemon unPokemon : listadoPokemones.learned_by_pokemon) {
                System.out.println(" - " + unPokemon.getName());
            }
        } else {
            System.out.println("No se pudo obtener el listado de Pokémon para el movimiento dado.");
        }
    }
}
