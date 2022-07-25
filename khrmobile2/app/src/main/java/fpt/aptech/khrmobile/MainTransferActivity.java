package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.MyTimelineAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.Transfer1Adapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainTransferActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_transfer);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main_transfer);

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,MainTransferActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,MainTransferActivity.this,0.8);



        buttonBack();
        btn_send_transfer();
        btn_cofirm();
        

    }



    private void btn_send_transfer(){
        Button button = findViewById(R.id.btn_send_transfer);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTransferActivity.this, MainRequestTransferActivity.class);
                startActivity(intent);
            }
        });
    }

    private  void btn_cofirm() {
        Button button = findViewById(R.id.btn_cofirm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTransferActivity.this, MainConfirmTransferActivity.class);
                startActivity(intent);
            }
        });
    }


    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainTransferActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}