package fpt.aptech.khrmobile;

import androidx.annotation.NonNull;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;

import fpt.aptech.khrmobile.Entities.Account;

public class MainActivity extends AppCompatActivity {
    Account account;

    SharedPreferences sharedPreferences;
    public static final String profilePreferences = "profilepref";
    public static final String Mail = "mailKey";
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Birthday = "birthKey";
    public static final String Gender = "genderKey";
    public static final String Avatar = "avatarKey";
    public static final String Code = "codeKey";

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
        }

        TextView username = findViewById(R.id.textView4);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            account = (Account) intent.getSerializableExtra("data");
            sharedPreferences = getSharedPreferences(profilePreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Mail, account.getMail());
            editor.putString(Name, account.getFullname());
            editor.putString(Phone, account.getPhone());
            editor.putString(Birthday, DateFormat.getDateInstance().format(account.getBirthdate()));
            editor.putString(Avatar, account.getAvatar());
            editor.putString(Code, account.getCode());
            editor.commit();
            String name = sharedPreferences.getString(MainActivity.Name,null);
            username.setText("Xin chào " + name);
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
        buttonHomeLogout();
        buttonDayOff();
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
    private void buttonDayOff(){
        Button button2 = findViewById(R.id.Home_Leaveoff);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DayOffActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonHomeLogout(){
        Button buttonLogout = findViewById(R.id.Home_Logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences myPrefs = getSharedPreferences(profilePreferences,
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.apply();
                finish();
                gsc.signOut();
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);

            }
        });
    }





}