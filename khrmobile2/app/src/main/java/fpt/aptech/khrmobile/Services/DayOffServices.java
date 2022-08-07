package fpt.aptech.khrmobile.Services;

import java.util.ArrayList;

import fpt.aptech.khrmobile.Entities.DayOff;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DayOffServices {
    @GET("dayoff/token")
    Call<ArrayList<DayOff>> findAll(@Query("token") String token);
    @GET("dayoff/token/search")
    Call<ArrayList<DayOff>> findByYear(@Query("token") String token, @Query("year") String year);
    @POST("dayoff/add")
    Call<DayOff> AddDayOff(@Body DayOff dayOff);
}
