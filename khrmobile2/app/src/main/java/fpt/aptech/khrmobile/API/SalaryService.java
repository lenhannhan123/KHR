package fpt.aptech.khrmobile.API;

import java.util.List;

import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.Entities.Timekeeping;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SalaryService {

    @GET("/api/salary/year/{mail}")
    Call<List<String>> getYear(@Path("mail") String mail);

    @GET("/api/salary/findOneByDate")
    Call<List<ModelString>> findOneByDate(@Query("mail") String mail, @Query("month") int month, @Query("year") int year);
}
