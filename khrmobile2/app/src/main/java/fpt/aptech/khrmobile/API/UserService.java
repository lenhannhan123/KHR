package fpt.aptech.khrmobile.API;

import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface UserService {
    @POST("api/auth/signin")
    Call<Account> loginUser(@Body LoginRequest loginRequest);
}
