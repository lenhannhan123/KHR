package fpt.aptech.khrmobile.Services;

import fpt.aptech.khrmobile.Entities.AccountToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TokenServices {
    @GET("token/get")
    Call<AccountToken> GetToken(@Query("token") String token);
    @POST("token/add")
    Call<AccountToken> AddToken(@Body AccountToken token);
}
