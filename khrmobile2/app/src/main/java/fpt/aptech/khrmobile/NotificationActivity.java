package fpt.aptech.khrmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import fpt.aptech.khrmobile.Entities.AccountNotification;
import fpt.aptech.khrmobile.ListBaseAdapter.AccountnotificationAdapter;
import fpt.aptech.khrmobile.Services.AccountNotifactionServices;
import fpt.aptech.khrmobile.ServicesImp.NotificationUtilAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<AccountNotification> accountNotifications;
    AccountNotifactionServices accountNotifactionServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_notification);
        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_2,NotificationActivity.this);
        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,NotificationActivity.this,0.8);
        buttonBack();
        recyclerView = findViewById(R.id.rcvnotifiaction);
        accountNotifactionServices= NotificationUtilAPI.getAccountNotifacationServices();
        GetTokenData();
    }
    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_change_infor_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void GetTokenData(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                //task.getResult();
                System.out.println(task.getResult());
                accountNotifactionServices.findAll( task.getResult()).enqueue(new Callback<ArrayList<AccountNotification>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AccountNotification>> call, Response<ArrayList<AccountNotification>> response) {
                        accountNotifications  = response.body();
                        SetRecycleView();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AccountNotification>> call, Throwable t) {
                        Toast.makeText(NotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private  void SetRecycleView(){
        AccountnotificationAdapter adapter = new AccountnotificationAdapter(accountNotifications,NotificationActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);


    }
}