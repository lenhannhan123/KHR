package fpt.aptech.khrmobile.API;
import java.util.concurrent.TimeUnit;

import fpt.aptech.khrmobile.Config.ConfigData;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://"+ ConfigData.IP +":7777/")
                .client(okHttpClient).build();
        return retrofit;
    }


    public static UserService getService(){
        UserService userService = getRetrofit().create(UserService.class);
        return userService;
    }

}
