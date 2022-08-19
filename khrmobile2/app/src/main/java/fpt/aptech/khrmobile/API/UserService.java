package fpt.aptech.khrmobile.API;

import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import fpt.aptech.khrmobile.Entities.ModelString;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserService {
    @Headers({"Accept: application/json"})
    @POST("/api/auth")
    Call<Account> loginUser(@Body LoginRequest loginRequest);

    @POST("api/accountLogin")
    Call<ModelString>  LoginAPI (@Query("mail") String mail,
                                 @Query("pass") String pass);


    @POST("/api/recover-code-mail")
    Call<Account> sendRecoverCodeMail(@Body String mail);

    @POST("/api/recover-code-sms")
    Call<Account> sendRecoverCodeSms(@Body Account account);

    @POST("/api/recovery-change-pass")
    Call<Account> recoveryChangePass(@Body Account account);

    @POST("/api/checkGoogleId")
    Call<Account> checkGoogleId(@Body Account account);

    @POST("/api/change-profile-info")
    Call<Account> changeProfileInfo(@Body Account account);

    @GET("/api/get-profile-info")
    Call<ModelString> getProfileInfo(@Query("mail") String mail);

    @Multipart
    @POST("api/update-photo-profile")
    Call<Account> uploadImage(@Part MultipartBody.Part file, @Part(Account.Key_mail) RequestBody mail);
}
