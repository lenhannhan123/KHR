package fpt.aptech.khrmobile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import fpt.aptech.khrmobile.API.APITimeline;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.Transfer1Adapter;
import fpt.aptech.khrmobile.ListBaseAdapter.Transfer2Adapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainConfirmTransferActivity extends AppCompatActivity {

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_confirm_transfer);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main_confirm_transfer);

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_1,MainConfirmTransferActivity.this);
        buttonBack();


        listView = findViewById(R.id.Transfer_ListView);
        GetData();
    }
    ConfigData configData = new ConfigData();

    private void GetData() {

        String mail =configData.userId(MainConfirmTransferActivity.this);

        APITimeline.api.GetReport2(mail).enqueue(new Callback<List<ModelString>>() {
            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                List<ModelString> data =response.body();
                Transfer2Adapter baseAdapter = new Transfer2Adapter(MainConfirmTransferActivity.this,data,MainConfirmTransferActivity.this);
                listView.setAdapter(baseAdapter);
                baseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {

            }
        });

    }


    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_Work_schedule_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainConfirmTransferActivity.this, MainTransferActivity.class);
                startActivity(intent);
            }
        });
    }
}