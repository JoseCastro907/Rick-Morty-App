package cr.ac.ucr.rickmorty.api;

import cr.ac.ucr.rickmorty.models.CharacterResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CharacterService {

    @Headers("Content-Type: application/json")
    @GET("character")
    Call<CharacterResponse> getCharacters();

}
