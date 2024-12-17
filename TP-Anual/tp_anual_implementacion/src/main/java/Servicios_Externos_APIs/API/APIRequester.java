package Servicios_Externos_APIs.API;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIRequester {
    private static APIRequester instancia = null;
    private static final String URL = "https://82258e2f-5189-49e7-a712-4d8e0f33e3ff.mock.pstmn.io/";



    public static String getUrlApi() {
        return URL;
    }
    private final Retrofit retrofit;

    private APIRequester(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(URL)
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