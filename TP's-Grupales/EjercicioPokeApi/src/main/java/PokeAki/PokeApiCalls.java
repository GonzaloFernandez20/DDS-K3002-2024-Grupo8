package PokeAki;
import dominio.Move;
import dominio.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeApiCalls {
    @GET("move/{name}")
    Call<Move> results(@Path("name") String name);

    @GET("pokemon/{name}")
    Call<Pokemon> traerPokemon (@Path("name") String name);
}
