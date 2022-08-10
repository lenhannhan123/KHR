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
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APITimeline {

//    Linh API:http://localhost:7777/api/timeline/

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    APITimeline api = new Retrofit.Builder()
            .baseUrl("http://"+ ConfigData.IP +":7777/api/timeline/")
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

    @GET("mytimeline/detail")
    Call<List<String>> getMyTimelineDetail (@Query("id") String id,
                                                 @Query("mail") String mail);

    @GET("addtimeline/list")
    Call<List<ModelString>> getAddTimeLine (@Query("mail") String mail);

    @GET("gettimelinedetail")
    Call<List<ModelString>> GetTimeLineSort (@Query("mail") String mail);

    @GET("sort/gettimeline")
    Call<List<String>>  GetTimeLineSort1 (@Query("id") String id,
                                              @Query("mail") String mail);

    @GET("sort/gettimelinedetail")
    Call<List<ModelString>>  GetTimeLineSortDetail (@Query("id") String id,
                                          @Query("shiftcode") String shiftcode);

    @GET("report/choose")
    Call<List<ModelString>> GetReportChooseTimeline (@Query("mail") String mail);
    @GET("report/mydate")
    Call<List<ModelString>> GetReportMyDate (@Query("mail") String mail,
                                                     @Query("id") String id
                                                     );

    @GET("report/myshift")
    Call<List<ModelString>> GetReportMyShift (@Query("mail") String mail,
                                             @Query("id") String id,
                                              @Query("number") String number
    );
    @GET("report/youruser")
    Call<List<ModelString>> GetReportYourUser (@Query("code") String code,
                                              @Query("id") String id,
                                              @Query("position") String position
    );

    @GET("report/myposition")
    Call<List<ModelString>> GetReportMyPosition (@Query("mail") String mail);

    @GET("report/checkposition")
    Call<List<ModelString>> GetReportCheckPosition (@Query("mycode") String mydate,
                                                    @Query("yourcode") String yourdate,
                                                    @Query("id") String id,
                                                    @Query("mymail") String mymail,
                                                    @Query("yourmail") String yourmail,
                                                    @Query("idpos") String idpos
    );

    @POST("createtimeuser")
    Call<List<String>> CreateTimeline (@Query("mail") String mail,
                                 @Query("idTimeline") String idTimeline,
                                 @Query("data1") String data1,
                                 @Query("data2") String data2,
                                 @Query("data3") String data3,
                                 @Query("data4") String data4,
                                 @Query("data5") String data5,
                                 @Query("data6") String data6,
                                 @Query("data7") String data7,
                                 @Query("data8") String data8,
                                 @Query("data9") String data9,
                                 @Query("data10") String data10,
                                 @Query("data11") String data11,
                                 @Query("data12") String data12,
                                 @Query("data13") String data13,
                                 @Query("data14") String data14,
                                 @Query("data15") String data15,
                                 @Query("data16") String data16,
                                 @Query("data17") String data17,
                                 @Query("data18") String data18,
                                 @Query("data19") String data19,
                                 @Query("data20") String data20,
                                 @Query("data21") String data21,
                                 @Query("data22") String data22,
                                 @Query("data23") String data23,
                                 @Query("data24") String data24,
                                 @Query("data25") String data25,
                                 @Query("data26") String data26,
                                 @Query("data27") String data27,
                                 @Query("data28") String data28,
                                 @Query("data29") String data29,
                                 @Query("data30") String data30,
                                 @Query("data31") String data31,
                                 @Query("data32") String data32,
                                 @Query("data33") String data33,
                                 @Query("data34") String data34,
                                 @Query("data35") String data35
    );
}

