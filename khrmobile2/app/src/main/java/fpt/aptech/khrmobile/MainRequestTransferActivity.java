package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainRequestTransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_request_transfer);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main_request_transfer);

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,MainRequestTransferActivity.this);
        buttonBack();
        add_request();
    }

    private void add_request(){
        ImageButton button = findViewById(R.id.add_request);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainRequestTransferActivity.this, CreateTransferActivity.class);
                startActivity(intent);
            }
        });
    }
    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainRequestTransferActivity.this, MainTransferActivity.class);
                startActivity(intent);
            }
        });
    }
}