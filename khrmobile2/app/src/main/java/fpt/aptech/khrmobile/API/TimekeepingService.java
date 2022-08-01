package fpt.aptech.khrmobile.API;

import java.util.List;

import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.Timekeeping;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TimekeepingService {
    @GET("/api/timekeeping/findAllByDate")
    Call<List<Timekeeping>> findAllByDate(@Path("month") int month, @Path("month") int year);

    @GET("/api/timekeeping/accountList")
    Call<List<Account>> accountList();

    @GET("/api/timekeeping/year/{mail}")
    Call<List<String>> getYear(@Path("mail") String mail);

    @GET("/api/timekeeping/findByAccount/{mail}")
    Call<List<Timekeeping>> findByAccount(@Path("mail") String mail);

    @POST("/api/timekeeping/checkin/{mail}")
    Call<Timekeeping> checkin(@Body Timekeeping timekeeping, @Path("mail") String mail);

    @POST("/api/timekeeping/checkout/{mail}")
    Call<Timekeeping> checkout(@Body Timekeeping timekeeping, @Path("mail") String mail);
}
