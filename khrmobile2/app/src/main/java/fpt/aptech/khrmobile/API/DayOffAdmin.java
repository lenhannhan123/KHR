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

public interface DayOffAdmin {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    DayOffAdmin api = new Retrofit.Builder()
            .baseUrl("http://" + ConfigData.IP + ":7777/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DayOffAdmin.class);
    @GET("dayoff/list/notcheck")
    Call<List<ModelString>> getListDayOff(@Query("id") String id);
    @GET("dayoff/list/approved")
    Call<List<ModelString>> ApprovedDayOff(@Query("id") String id);
    @GET("dayoff/list/denying")
    Call<List<ModelString>> DenyinggetListDayOff(@Query("id") String id);
}
