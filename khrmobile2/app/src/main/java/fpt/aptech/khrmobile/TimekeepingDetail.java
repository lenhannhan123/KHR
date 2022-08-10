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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fpt.aptech.khrmobile.API.TimekeepingService;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.Entities.Timekeeping;
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

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://" + ConfigData.IP + ":7777/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        service = retrofit.create(TimekeepingService.class);
        Call<Integer> call = service.detailId(timekeeping.getId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Retrofit retrofit = new Retrofit.Builder().
                            baseUrl("http://" + ConfigData.IP + ":7777/").
                            addConverterFactory(GsonConverterFactory.create()).
                            build();
                    service = retrofit.create(TimekeepingService.class);
                    Call<List<String>> _call = service.detail(response.body());
                    _call.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().get(1).equals("06")) {
                                    txtTimekeepingName.setText("Ca sáng");
                                } else if (response.body().get(1).equals("10")) {
                                    txtTimekeepingName.setText("Ca trưa");
                                } else if (response.body().get(1).equals("14")) {
                                    txtTimekeepingName.setText("Ca chiều");
                                } else if (response.body().get(1).equals("18")) {
                                    txtTimekeepingName.setText("Ca tối");
                                } else if (response.body().get(1).equals("22")) {
                                    txtTimekeepingName.setText("Ca đêm");
                                }
                                txtAccountPosition.setText(response.body().get(2));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

        txtTimekeepingName = findViewById(R.id.txtTimekeepingName);
        txtAccountPosition = findViewById(R.id.txtAccountPosition);


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