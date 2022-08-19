package fpt.aptech.khrmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuChangeAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_change_account);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_menu_change_account_layout);

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_3,MenuChangeAccountActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,MenuChangeAccountActivity.this,0.8);

        buttonBack();
        buttonChangeInfo();
        buttonChangeAvatar();
    }

    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_change_account_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChangeAccountActivity.this, MainAccountActivity.class);
                startActivity(intent);
            }
        });
    }


    private void buttonChangeInfo(){
        Button button = findViewById(R.id.Accoun_change_btnInfor);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChangeAccountActivity.this, ChangeInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buttonChangeAvatar(){
        Button button = findViewById(R.id.Accoun_change_btnAvt);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuChangeAccountActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
        });
    }








}