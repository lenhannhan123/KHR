package fpt.aptech.khrmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import fpt.aptech.khrmobile.API.DayOffAdmin;
import fpt.aptech.khrmobile.API.MessingAPI;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.DayOffAdminAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.MessageListItemAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.MessingAccountAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDayOffActivity extends AppCompatActivity {
    Context context;
    RecyclerView recyclerdayoff;
    List<ModelString> DayoffList,MessageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_day_off);
        recyclerdayoff=findViewById(R.id.rcv_dayoff_item);
        showContact();

    }
    private void showContact(){
        DayOffAdmin.api.getListDayOff("1").enqueue(new Callback<List<ModelString>>() {
            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                if(response.isSuccessful()){
                    DayoffList = response.body();
                    DayOffAdminAdapter messingAccountAdapter = new DayOffAdminAdapter   (DayoffList,AdminDayOffActivity.this);
                    context = getApplicationContext();
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerdayoff.setLayoutManager(mLayoutManager);
                    recyclerdayoff.setItemAnimator(new DefaultItemAnimator());
                    recyclerdayoff.setAdapter(messingAccountAdapter);
                }else {
                    Toast.makeText(AdminDayOffActivity.this, "faill!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {
                Toast.makeText(AdminDayOffActivity.this, "Connect error, unable to find classes!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}