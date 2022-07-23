package fpt.aptech.khrmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fpt.aptech.khrmobile.Entities.Account;

public class MainActivity extends AppCompatActivity {
    Account account;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.textView4);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            account = (Account) intent.getSerializableExtra("data");
            username.setText("Xin chào " + account.getFullname());
            Log.e("TAG", "===>" + account.getFullname());
        }

        getSupportActionBar().hide();
//        setDisplay();
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,MainActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,MainActivity.this,0.88);

        buttonWorkSchedule();
    }



    private void buttonWorkSchedule(){
        Button button1 = findViewById(R.id.Home_btnWorkSchedule);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainWorkScheduleActivity.class);
                startActivity(intent);
            }
        });
    }





}