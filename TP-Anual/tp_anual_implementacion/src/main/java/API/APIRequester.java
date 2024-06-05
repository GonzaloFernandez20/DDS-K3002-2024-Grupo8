package API;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIRequester {
    private static APIRequester instancia = null;
    private static final String urlApi = "https://9dc74674-32b4-4a0c-9a42-b49f62b65aef.mock.pstmn.io/";
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

    public ResponseRecomendacion obtenerPuntosRecomendados(double latitud, double longitud, int radio) throws IOException {
        MockApiCalls mockApiCall = this.retrofit.create(MockApiCalls.class);
        Call<ResponseRecomendacion> apiCall = mockApiCall.obtenerPuntosPosiblesDeColocacion(latitud, longitud, radio);
        Response<ResponseRecomendacion> respuestaApiCall = apiCall.execute();
    
        ResponseRecomendacion listadoDePuntos = respuestaApiCall.body();
        return listadoDePuntos;
    }
}

interface MockApiCalls {
    @GET("puntoRecomendado")
    Call<ResponseRecomendacion> obtenerPuntosPosiblesDeColocacion(
        @Query("latitud") double latitud,
        @Query("longitud") double longitud,
        @Query("radio") int radio
    );
}