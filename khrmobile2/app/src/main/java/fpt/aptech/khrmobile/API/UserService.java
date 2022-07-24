package fpt.aptech.khrmobile.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface UserService {
    @Headers({"Accept: application/json"})
    @POST("/api/auth")
    Call<Account> loginUser(@Body LoginRequest loginRequest);
}
