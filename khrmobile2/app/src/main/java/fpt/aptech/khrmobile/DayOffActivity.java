package fpt.aptech.khrmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.ar.core.Anchor;
//import com.anychart.core.cartesian.series.Column;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import fpt.aptech.khrmobile.Entities.DayOff;
import fpt.aptech.khrmobile.ListBaseAdapter.DayoffAdapter;
import fpt.aptech.khrmobile.Services.DayOffServices;
import fpt.aptech.khrmobile.ServicesImp.DayOffUtillAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayOffActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    String[] item = {"2019","2020","2021","2022"};
    android.widget.ArrayAdapter<String> ArrayAdapter;
    DayOffServices dayOffServices;
    List<DayOff> dayOffsList;
    RecyclerView recyclerView;
    Button addbuton;
    TextView tvtotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_off);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_day_off);
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,DayOffActivity.this);
        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,DayOffActivity.this,0.8);
        buttonBack();

        addbuton=findViewById(R.id.btnregesdayoff);
        recyclerView = findViewById(R.id.rvcdayoff);
        tvtotal = findViewById(R.id.textTotalday);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        dayOffServices = DayOffUtillAPI.getAccountDayoff();
        setDropItem();
        GetTokenData();
        getchar();
        addbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayOffActivity.this, RegisterDayoffActivity.class);
                startActivity(intent);
            }
        });

    }
    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_change_infor_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DayOffActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setDropItem(){
        ArrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item,item);
        autoCompleteTextView.setAdapter(ArrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String items = adapterView.getItemAtPosition(i).toString();
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        dayOffServices.findByYear(task.getResult(),items).enqueue(new Callback<ArrayList<DayOff>>() {
                            @Override
                            public void onResponse(Call<ArrayList<DayOff>> call, Response<ArrayList<DayOff>> response) {
                                dayOffsList  = response.body();
                                LinearLayoutManager layoutManager = new LinearLayoutManager(DayOffActivity.this);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                DayoffAdapter adapter = new DayoffAdapter(dayOffsList,DayOffActivity.this);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setHasFixedSize(true);
                                tvtotal.setText("Tổng ngày nghĩ : "+gettotal(dayOffsList));
                            }

                            @Override
                            public void onFailure(Call<ArrayList<DayOff>> call, Throwable t) {
                                Toast.makeText(DayOffActivity.this, "That Bai", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
    private void GetTokenData(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                dayOffServices.findAll(task.getResult()).enqueue(new Callback<ArrayList<DayOff>>() {
                    @Override
                    public void onResponse(Call<ArrayList<DayOff>> call, Response<ArrayList<DayOff>> response) {
                        dayOffsList  = response.body();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(DayOffActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        DayoffAdapter adapter = new DayoffAdapter(dayOffsList,DayOffActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        tvtotal.setText("Tổng ngày nghĩ : "+gettotal(dayOffsList));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<DayOff>> call, Throwable t) {
                        Toast.makeText(DayOffActivity.this, "That Bai", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private int gettotal(List<DayOff> dayList){
        int dem = 0;
        for (DayOff off: dayList
        ) {
            if (off.getStatus()==1){
                dem = dem + off.getDaynumber();
            }
        }
        return dem;
    }
    public void getchar(){
        BarChart chart = findViewById(R.id.barchart);

        ArrayList NoOfEmp = new ArrayList();

        NoOfEmp.add(new BarEntry(945f, 0));
        NoOfEmp.add(new BarEntry(1040f, 1));
        NoOfEmp.add(new BarEntry(1133f, 2));
        NoOfEmp.add(new BarEntry(1240f, 3));
        NoOfEmp.add(new BarEntry(1369f, 4));
        NoOfEmp.add(new BarEntry(1487f, 5));
        NoOfEmp.add(new BarEntry(1501f, 6));
        NoOfEmp.add(new BarEntry(1645f, 7));
        NoOfEmp.add(new BarEntry(1578f, 8));
        NoOfEmp.add(new BarEntry(1695f, 9));

        ArrayList listyear = new ArrayList();
        listyear.add("2008");
        listyear.add("2009");
        listyear.add("2010");
        listyear.add("2011");
        listyear.add("2012");
        listyear.add("2013");
        listyear.add("2014");
        listyear.add("2015");
        listyear.add("2016");
        listyear.add("2017");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "No Of Employee");
        ///BarDataSet bardataset2 = new BarDataSet(listyear, "year");
        chart.animateY(5000);
        BarData data = new BarData(bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }
}