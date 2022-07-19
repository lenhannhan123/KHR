package fpt.aptech.khrmobile.APII;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import fpt.aptech.khrmobile.Entities.ModelString;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APITimeline {

//    Linh API:http://localhost:7777/api/timeline/

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    APITimeline api = new Retrofit.Builder()
            .baseUrl("http://192.168.1.201:7777/api/timeline/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APITimeline.class);


    @GET("getyear")
    Call<List<Integer>> getTimelineyear ();

    @GET("mytimeline")
    Call<List<ModelString>> getMyTimeline (@Query("id") String id,
                                           @Query("month") String month,
                                           @Query("year") String year
                                      );


}
