package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fpt.aptech.khrmobile.API.TimekeepingService;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.Timekeeping;
import fpt.aptech.khrmobile.ListBaseAdapter.TimekeepingBaseAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimekeepingActivity extends AppCompatActivity {
    TimekeepingService service;
    Spinner spinnerMonth, spinnerYear;
    List<Timekeeping> timekeepings;
    ListView listView;
    TimekeepingBaseAdapter timekeepingBaseAdapter;
    Button btnSearchTimekeeping;
    int month = 0;
    int year = 0;
    public static final String profilePreferences = "profilepref";
    SharedPreferences sharedPreferences;

    private void setSpinnerMonth() {
        spinnerMonth = findViewById(R.id.spinnerMonth);
        List<String> months = new ArrayList<>();
        months.add("Chọn tháng");
        for (int i = 1; i <= 12; i++) {
            months.add("Tháng " + String.valueOf(i));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, months);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerMonth.setAdapter(arrayAdapter);

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerYear() {
        spinnerYear = findViewById(R.id.spinnerYear);
        List<String> years = new ArrayList<>();
        years.add("Chọn năm");
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://" + ConfigData.IP + ":7777/").
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        service = retrofit.create(TimekeepingService.class);
        Call<List<String>> call = service.getYear(sharedPreferences.getString(MainActivity.Mail,null));
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null){
                    years.add(response.body().toString().replace("[", "").replace("]", ""));
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TimekeepingActivity.this);
                builder.setTitle("Error");
                builder.setMessage(t.getMessage());
                builder.show();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, years);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinnerYear.setAdapter(arrayAdapter);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getSelectedItem().toString();
//                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getTimekeepings() {
        listView = findViewById(R.id.listView);
        timekeepings = new ArrayList<>();
        timekeepings.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ConfigData.IP + ":7777/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(TimekeepingService.class);
        Call<List<Timekeeping>> call = service.findByAccount(sharedPreferences.getString(MainActivity.Mail,null));
        call.enqueue(new Callback<List<Timekeeping>>() {
            @Override
            public void onResponse(Call<List<Timekeeping>> call, Response<List<Timekeeping>> response) {
                timekeepings.addAll(response.body());
                timekeepingBaseAdapter = new TimekeepingBaseAdapter(timekeepings, getApplicationContext());
                timekeepingBaseAdapter.notifyDataSetChanged();
                listView.setAdapter(timekeepingBaseAdapter);
            }

            @Override
            public void onFailure(Call<List<Timekeeping>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TimekeepingActivity.this);
                builder.setTitle("Error");
                builder.setMessage(t.getMessage());
                builder.show();
            }
        });
    }

    private void searchTimekeeping(){
        btnSearchTimekeeping = findViewById(R.id.btnSearchTimekeeping);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        listView = findViewById(R.id.listView);
        timekeepings = new ArrayList<>();
        month = 0;
        year = 0;

        btnSearchTimekeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(spinnerYear.getSelectedItem().equals("Chọn năm")){
                    year = 0;
                }else{
                    year = Integer.parseInt(spinnerYear.getSelectedItem().toString());
                }

                //int year = Integer.parseInt(spinnerYear.getSelectedItem().toString());

                for (int i = 0; i <= 12; i++){
                    if(spinnerMonth.getSelectedItem().toString().contains(String.valueOf(i))){
                        month = i;
                    }else if(spinnerMonth.getSelectedItem().toString().equals("Chọn tháng")){
                        month = 0;
                    }
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://" + ConfigData.IP + ":7777/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                service = retrofit.create(TimekeepingService.class);
                Call<List<Timekeeping>> call = service.findAllByDate(sharedPreferences.getString(MainActivity.Mail,null), month, year);
                call.enqueue(new Callback<List<Timekeeping>>() {
                    @Override
                    public void onResponse(Call<List<Timekeeping>> call, Response<List<Timekeeping>> response) {
                        if(response.isSuccessful() && month != 0 && year != 0){
                            timekeepings.clear();
                            timekeepings.addAll(response.body());
                            timekeepingBaseAdapter = new TimekeepingBaseAdapter(timekeepings, getApplicationContext());
                            timekeepingBaseAdapter.notifyDataSetChanged();
                            listView.setAdapter(timekeepingBaseAdapter);
                        }else if(month == 0){
                            Toast.makeText(getApplicationContext(), "Vui lòng chọn tháng", Toast.LENGTH_LONG).show();
                        }else if(year == 0){
                            Toast.makeText(getApplicationContext(), "Vui lòng chọn năm", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Timekeeping>> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timekeeping);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_timekeeping);
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,TimekeepingActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,TimekeepingActivity.this,0.8);
        ImageButton historyBackBtnInTimekeeping = findViewById(R.id.historyBackBtnInTimekeeping);
        historyBackBtnInTimekeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimekeepingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences(profilePreferences, Context.MODE_PRIVATE);
        setSpinnerMonth();
        setSpinnerYear();
        getTimekeepings();
        searchTimekeeping();



    }
}