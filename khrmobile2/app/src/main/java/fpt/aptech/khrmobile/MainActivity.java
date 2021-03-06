package fpt.aptech.khrmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;

import fpt.aptech.khrmobile.Entities.Account;

public class MainActivity extends AppCompatActivity {
    Account account;
    TextView username;
    Intent intent;

    SharedPreferences sharedPreferences;
    public static final String profilePreferences = "profilepref";
    public static final String Mail = "mailKey";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Birthday = "birthKey";
    public static final String Gender = "genderKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.textView4);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            account = (Account) intent.getSerializableExtra("data");
//            username.setText("Xin chào " + account.getFullname());
            sharedPreferences = getSharedPreferences(profilePreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Mail, account.getMail());
            editor.putString(Name, account.getFullname());
            editor.putString(Phone, account.getPhone());
            editor.putString(Birthday, DateFormat.getDateInstance().format(account.getBirthdate()));
            editor.commit();
            String namekey = sharedPreferences.getString(MainActivity.Name,null);
            username.setText("Xin chào " + namekey);
//            Log.e("TAG", "===>" + account.getPhone());


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