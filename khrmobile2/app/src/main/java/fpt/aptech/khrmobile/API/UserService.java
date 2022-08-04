package fpt.aptech.khrmobile.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserService {
    @Headers({"Accept: application/json"})
    @POST("/api/auth")
    Call<Account> loginUser(@Body LoginRequest loginRequest);

    @POST("/api/recover-code-mail")
    Call<Account> sendRecoverCodeMail(@Body String mail);

    @POST("/api/recover-code-sms")
    Call<Account> sendRecoverCodeSms(@Body Account account);

    @POST("/api/recovery-change-pass")
    Call<Account> recoveryChangePass(@Body Account account);
}
