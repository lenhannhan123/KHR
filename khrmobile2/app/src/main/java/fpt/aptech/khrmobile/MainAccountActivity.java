package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fpt.aptech.khrmobile.Entities.Account;

public class MainAccountActivity extends AppCompatActivity {
    Account account;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);

        TextView username = findViewById(R.id.tvUsername);
        TextView mail = findViewById(R.id.tvMail);
        TextView phone = findViewById(R.id.tvPhone);
        TextView birthday = findViewById(R.id.tvBirthday);
        TextView gender = findViewById(R.id.tvGender);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main_account_layout);

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.profilePreferences, Context.MODE_PRIVATE);

        String namekey = sharedpreferences.getString(MainActivity.Name,null);
        String mailkey = sharedpreferences.getString(MainActivity.Mail,null);
        String phonekey = sharedpreferences.getString(MainActivity.Phone,null);
        String birthdaykey = sharedpreferences.getString(MainActivity.Birthday,null);

        username.setText(namekey);
        mail.setText(mailkey);
        phone.setText(phonekey);
        birthday.setText(birthdaykey);




        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_3,MainAccountActivity.this);


        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,MainAccountActivity.this,0.8);

        buttonchange();
    }

    private void buttonchange(){
        ImageButton button = findViewById(R.id.main_account_button_change);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAccountActivity.this, MenuChangeAccountActivity.class);
                startActivity(intent);
            }
        });
    }



}