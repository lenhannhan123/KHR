package fpt.aptech.khrmobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
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

    private void setSpinnerMonth() {
        spinnerMonth = findViewById(R.id.spinnerMonth);
        List<String> months = new ArrayList<>();
        months.add("Chọn tháng");
        for (int i = 0; i < 12; i++) {
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
        Call<List<String>> call = service.getYear("thanhnhan@gmail.com");
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                years.add(response.body().toString().replace("[", "").replace("]", ""));
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
        Call<List<Timekeeping>> call = service.findByAccount("thanhnhan@gmail.com");
        call.enqueue(new Callback<List<Timekeeping>>() {
            @Override
            public void onResponse(Call<List<Timekeeping>> call, Response<List<Timekeeping>> response) {
                timekeepings.addAll(response.body());
                timekeepingBaseAdapter = new TimekeepingBaseAdapter(timekeepings);
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
        Spinner spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerYear = findViewById(R.id.spinnerYear);
        listView = findViewById(R.id.listView);
        timekeepings = new ArrayList<>();
        timekeepings.clear();

        btnSearchTimekeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = spinnerMonth.getSelectedItem().toString();
                String year = spinnerYear.getSelectedItem().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://" + ConfigData.IP + ":7777/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                service = retrofit.create(TimekeepingService.class);
                Call<List<Timekeeping>> call = service.findAllByDate(Integer.parseInt(month), Integer.parseInt(year));
                call.enqueue(new Callback<List<Timekeeping>>() {
                    @Override
                    public void onResponse(Call<List<Timekeeping>> call, Response<List<Timekeeping>> response) {
                        timekeepings.addAll(response.body());
                        timekeepingBaseAdapter = new TimekeepingBaseAdapter(timekeepings);
                        timekeepingBaseAdapter.notifyDataSetChanged();
                        listView.setAdapter(timekeepingBaseAdapter);
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

        setSpinnerMonth();
        setSpinnerYear();
        getTimekeepings();
        searchTimekeeping();
    }
}