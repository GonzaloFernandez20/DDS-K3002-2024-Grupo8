package PokeAki;

import java.io.IOException;

import dominio.Move;
import dominio.Pokemon;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.ImprimirListaPokemons;
import utils.ImprimirPokemon;

public class APIRequester {
    private static APIRequester instancia = null;
    private static final String urlApi = "https://pokeapi.co/api/v2/";
    private final Retrofit retrofit;

    private APIRequester(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIRequester getInstancia() {
        if (instancia == null) {
            instancia = new APIRequester();
        }
        return instancia;
    }

    // ACA ME TRAIGO UN POKEMON DADO UN NOMBRE
    public void obtenerPokemonPorNombre(String nombre) throws IOException {

        PokeApiCalls pokemonAPI = this.retrofit.create(PokeApiCalls.class);
        Call<Pokemon> pokemonCall = pokemonAPI.traerPokemon(nombre);
        Response<Pokemon> respuestaPokemon = pokemonCall.execute();

        Pokemon pokemon = respuestaPokemon.body();
        ImprimirPokemon.imprimirPokemon(pokemon);
    }

    // ACA ME TRAIGO LOS POKEMONES ASOCIADOS A UN MOVIMIENTO
    public void listadoDePokemosPorMovimiento(String name) throws IOException{

        PokeApiCalls movAPI = this.retrofit.create(PokeApiCalls.class);
        Call<Move> listadoMovimientos = movAPI.results(name);
        Response<Move> respuestaMovimientos = listadoMovimientos.execute();
        Move listadoDeMovimientos = respuestaMovimientos.body();

        System.out.println("Nombre del movimiento: " + name);
        ImprimirListaPokemons.imprimirPokemonesPorMovimiento(listadoDeMovimientos);
    }
}