package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.AddTimelineAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.ScheduleAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkScheduleDetailActivity extends AppCompatActivity {
    String data_;
    String idSelect="";
    String Code = "";
    List<ModelString> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_schedule_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_schedule_timeline_detail);
        Intent intent = getIntent();
         data_  = intent.getStringExtra("data");
        idSelect  = intent.getStringExtra("idSelect");
        Code  =intent.getStringExtra("Code") ;
  

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,WorkScheduleDetailActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,WorkScheduleDetailActivity.this,0.8);

        buttonBack();
        GetData();
    }

    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkScheduleDetailActivity.this, AddScheduleTimelineDetailActivity.class);
                intent.putExtra("data", data_);
                intent.putExtra("idSelect", idSelect);

                startActivity(intent);
            }
        });
    }

    ConfigData configData = new ConfigData();
    private  void GetData(){
        String mail = configData.userId(this);

        APITimeline.api.GetTimeLineSortDetail(idSelect,Code).enqueue(new Callback<List<ModelString>>() {
            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                data = response.body();
//                Toast.makeText(WorkScheduleDetailActivity.this, ""+String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
                ListView listView = findViewById(R.id.Add_Time_ListView);
                ScheduleAdapter baseAdapter = new ScheduleAdapter(WorkScheduleDetailActivity.this,data,WorkScheduleDetailActivity.this);
                listView.setAdapter(baseAdapter);
                baseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {
                Toast.makeText(WorkScheduleDetailActivity.this,"Lỗi mạng!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}