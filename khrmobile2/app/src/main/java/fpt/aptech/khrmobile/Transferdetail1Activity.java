package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Entities.ModelString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transferdetail1Activity extends AppCompatActivity {
    String idSelect="";

    ModelString modelString;


    TextView time,dayfrom,dayto,title,position1,position2,mail1,mail2,content,status,response1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferdetail1);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_transfer_detail1);

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation, R.id.page_1, Transferdetail1Activity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView, Transferdetail1Activity.this, 0.8);


        Intent intent = getIntent();
        idSelect  = intent.getStringExtra("idSelect");



        buttonBack();

//        time,dayfrom,dayto,title,position1,position2,mail1,mail2,content,status,response;
        time= findViewById(R.id.Time);
        dayfrom= findViewById(R.id.dayfrom);
        dayto= findViewById(R.id.dayto);
        position1= findViewById(R.id.position1);
        position2= findViewById(R.id.position2);
        mail1= findViewById(R.id.mail1);
        mail2= findViewById(R.id.mail2);
        content= findViewById(R.id.content);
        status= findViewById(R.id.status);
        response1= findViewById(R.id.response);
        title= findViewById(R.id.title);

        GetData();

    }

    private void GetData() {
        APITimeline.api.GetReport1Detail(idSelect).enqueue(new Callback<List<ModelString>> () {
            @Override
            public void onResponse(Call<List<ModelString>>  call, Response<List<ModelString>>  response) {
                List<ModelString> modelStringList = response.body();
                modelString = modelStringList.get(0);

                String status1 = "";
                int color= Color.parseColor("#a8a8a8");

                switch (modelString.getData10()){

                    case "0":
                        status1="Người đổi đang xác nhận";
                        color= Color.parseColor("#d4db0d");
                        break;
                    case "1":
                        status1="Đang đợi admin  xác nhận";
                        color= Color.parseColor("#d4db0d");
                        break;
                    case "2":
                        status1="Người đổi từ chối";
                        color=Color.parseColor("#d81919");
                        break;
                    case "3":
                        status1="Đã đổi thành công";
                        color= Color.parseColor("#26b20f");
                        break;
                    default:
                        status1="Admin từ chối";
                        color=Color.parseColor("#d81919");
                        break;

                }





                title.setText(modelString.getData1());
                time.setText(modelString.getData2());
                dayfrom.setText(modelString.getData3());
                dayto.setText(modelString.getData4());
                position1.setText(modelString.getData5());
                position2.setText(modelString.getData6());
                mail1.setText(modelString.getData7());
                mail2.setText(modelString.getData8());
                content.setText(modelString.getData9());
                status.setText(status1);
                status.setTextColor(color);
                response1.setText(modelString.getData11());

            }

            @Override
            public void onFailure(Call<List<ModelString>>  call, Throwable t) {

            }
        });
    }

    private void buttonBack() {
        ImageButton button = findViewById(R.id.btn_change_infor_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Transferdetail1Activity.this, MainRequestTransferActivity.class);
                startActivity(intent);
            }
        });
    }
}