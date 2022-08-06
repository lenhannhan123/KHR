package fpt.aptech.khrmobile.Services;

import java.util.ArrayList;

import fpt.aptech.khrmobile.Entities.AccountNotification;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AccountNotifactionServices {
    @GET("notification/token")
    Call<ArrayList<AccountNotification>> findAll(@Query("token") String token);
}
