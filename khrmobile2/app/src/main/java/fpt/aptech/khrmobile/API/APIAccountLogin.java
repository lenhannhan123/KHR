package fpt.aptech.khrmobile.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIAccountLogin {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    APIAccountLogin api = new Retrofit.Builder()
            .baseUrl("http://"+ConfigData.IP+":7777/api/auth/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIAccountLogin.class);


    @GET("signin")
    Call<String> getLogin (@Query("mail") String mail,
                           @Query("password") String password);
}
