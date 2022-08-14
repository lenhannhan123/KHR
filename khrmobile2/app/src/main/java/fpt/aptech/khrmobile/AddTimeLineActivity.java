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

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.AddTimelineAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.MyTimelineAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AddTimeLineActivity extends AppCompatActivity {

    List<ModelString> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_line);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_add_timeline);
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,AddTimeLineActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,AddTimeLineActivity.this,0.8);

        GetData();
        buttonBack();
    }

    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTimeLineActivity.this, MainWorkScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    ConfigData configData = new ConfigData();
    private  void GetData(){
        String mail = configData.userId(this);

        APITimeline.api.getAddTimeLine(mail).enqueue(new Callback<List<ModelString>>() {
            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                data = response.body();
               ListView listView = findViewById(R.id.Add_Time_ListView);
                AddTimelineAdapter baseAdapter = new AddTimelineAdapter(AddTimeLineActivity.this,data,AddTimeLineActivity.this);
                listView.setAdapter(baseAdapter);
                baseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {
                Toast.makeText(AddTimeLineActivity.this,"Lỗi mạng!!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}