package fpt.aptech.khrmobile.API;

import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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

    @POST("/api/checkGoogleId")
    Call<Account> checkGoogleId(@Body Account account);
}
