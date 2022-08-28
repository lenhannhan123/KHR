package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import fpt.aptech.khrmobile.API.TimekeepingService;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.Entities.Timekeeping;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimekeepingDetail extends AppCompatActivity {
    TextView txtTimekeepingName, txtTimekeepingDate, txtAccountPosition, txtTimeStart, txtTimeEnd;
    Timekeeping timekeeping;
    TimekeepingService service;

    private void setDetail() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            timekeeping = (Timekeeping) intent.getSerializableExtra("data");
        }
        txtTimekeepingDate = findViewById(R.id.txtTimekeepingDate);
        String date = simpleDateFormat.format(timekeeping.getTimestart());
        txtTimekeepingDate.setText(date);
        txtTimeStart = findViewById(R.id.txtTimeStart);
        String timeStart = dateFormat.format(timekeeping.getTimestart());
        txtTimeStart.setText(timeStart);
        txtTimeEnd = findViewById(R.id.txtTimeEnd);
        String timeEnd = dateFormat.format(timekeeping.getTimeend());
        txtTimeEnd.setText(timeEnd);

        txtTimekeepingName = findViewById(R.id.txtTimekeepingName);
        if (timekeeping.getShiftCode() % 5 == 0) {
            txtTimekeepingName.setText("Ca sáng");
        } else if (timekeeping.getShiftCode() % 5 == 1) {
            txtTimekeepingName.setText("Ca trưa");
        } else if (timekeeping.getShiftCode() % 5 == 2) {
            txtTimekeepingName.setText("Ca chiều");
        } else if (timekeeping.getShiftCode() % 5 == 3) {
            txtTimekeepingName.setText("Ca tối");
        } else if (timekeeping.getShiftCode() % 5 == 4) {
            txtTimekeepingName.setText("Ca đêm");
        }

        int shiftCode = timekeeping.getShiftCode();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://" + ConfigData.IP + ":7777/").
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        service = retrofit.create(TimekeepingService.class);
        Call<ResponseBody> call = service.detail(timekeeping.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //JsonObject item = new JsonObject().get(response.body().toString()).getAsJsonObject();
                    txtAccountPosition = findViewById(R.id.txtAccountPosition);
                    try {
                        txtAccountPosition.setText(response.body().string().replaceAll("\"", ""));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(TimekeepingDetail.this, res, Toast.LENGTH_LONG).show();
                    //txtTimekeepingName.setText(res);
                } else {
                    Toast.makeText(TimekeepingDetail.this, "that bai", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(TimekeepingDetail.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timekeeping_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_timekeeping_detail);

        ImageButton historyBackBtnInTimekeeping = findViewById(R.id.historyBackBtnInTimekeepingDetail);
        historyBackBtnInTimekeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimekeepingDetail.this, TimekeepingActivity.class));

            }
        });

        setDetail();

    }
}