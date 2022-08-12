package fpt.aptech.khrmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.Entities.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Account account;
    TextView username;


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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null){
            String personName = acct.getDisplayName();
            String personId = acct.getId();
            Log.e("TAG", "===>" + personId);
            username = findViewById(R.id.textView4);
            username.setText("Xin chào " + personName);
            //Call API to check email and google id
            //get account info and save it to sharePreferences (locate in checkGoogleId)
            Account account = new Account();
            account.setMail(acct.getEmail());
            account.setGoogleid(acct.getId());
            checkGoogleId(account);
        }

        username = findViewById(R.id.textView4);

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
            editor.putString(Gender, String.valueOf(account.isGender()));
            editor.commit();
            String name = sharedPreferences.getString(MainActivity.Name,null);
            username.setText("Xin chào " + name);
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
        Home_btnTransfer();
        timekeepingBtn();
    }

    private void checkGoogleId(Account account){
        Call<Account> call = ApiClient.getService().checkGoogleId(account);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account = response.body();
                    //If account return matched Backend with Google ID, save backend data into sharedpreference
                    sharedPreferences = getSharedPreferences(profilePreferences, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Mail, account.getMail());
                    editor.putString(Name, account.getFullname());
                    editor.putString(Phone, account.getPhone());
                    editor.putString(Birthday, DateFormat.getDateInstance().format(account.getBirthdate()));
                    editor.putString(Avatar, account.getAvatar());
                    editor.putString(Code, account.getCode());
                    editor.putString(Gender, String.valueOf(account.isGender()));
                    editor.commit();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                String message="An error occured, please try again later..";
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }

    private void timekeepingBtn(){
        Button button1 = findViewById(R.id.Home_btnTimekeeping);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimekeepingActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Home_btnTransfer(){
        Button button1 = findViewById(R.id.Home_btnTransfer);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainTransferActivity.class);
                startActivity(intent);
            }
        });
    }






}