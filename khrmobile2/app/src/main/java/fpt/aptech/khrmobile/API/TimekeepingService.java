package fpt.aptech.khrmobile.API;

import fpt.aptech.khrmobile.Entities.Timekeeping;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TimekeepingService {
    @POST("/api/timekeeping/checkin")
    Call<Timekeeping> checkin(@Body Timekeeping timekeeping);

    @POST("/api/timekeeping/checkout")
    Call<Timekeeping> checkout(@Body Timekeeping timekeeping);

}
