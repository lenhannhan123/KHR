package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainWorkScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_work_schedule);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main_work_schedule);

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,MainWorkScheduleActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,MainWorkScheduleActivity.this,0.8);

        buttonBack();
        button_my_timeline();
        button_add_timeline();
    }



    private void button_my_timeline(){
        Button button = findViewById(R.id.btn_schedule_my_timeline);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainWorkScheduleActivity.this, MyTimelineActivity.class);
                startActivity(intent);
            }
        });
    }

    private void button_add_timeline(){
        Button button = findViewById(R.id.work_schedule_add_timeline);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainWorkScheduleActivity.this, AddTimeLineActivity.class);
                startActivity(intent);
            }
        });
    }


    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainWorkScheduleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}