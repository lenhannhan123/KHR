package fpt.aptech.khrmobile.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessingAPI {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    MessingAPI api = new Retrofit.Builder()
            .baseUrl("http://" + ConfigData.IP + ":7777/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MessingAPI.class);


    @GET("message/contact")
    Call<List<ModelString>> getContact(@Query("mail") String mail);

    @GET("message/account")
    Call<List<ModelString>> getMessage(@Query("send") String send, @Query("to") String to);
    @GET("message/list")
    Call<List<ModelString>> getMessageList(@Query("send") String send);
    @GET("message/send")
    Call<List<ModelString>> SendMessage(@Query("content") String content,@Query("send") String send, @Query("to") String to);
}