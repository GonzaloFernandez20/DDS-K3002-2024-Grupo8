import java.io.IOException;

import PokeAki.APIRequester;

public class Application {
    // ---------------------------------------- FUNCIONALIDADES ---------------------------------------- //
    public static void main(String[] args) throws IOException {
        try {
            APIRequester apiPokemon = APIRequester.getInstancia();

            apiPokemon.obtenerPokemonPorNombre("ditto");
            apiPokemon.listadoDePokemosPorMovimiento("transform");

        } catch (IOException e) {
            System.out.println("Error al conectarse a la API de Pokémon: " + e.getMessage());
        }
    }
}
