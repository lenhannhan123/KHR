package fpt.aptech.khrmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;

import fpt.aptech.khrmobile.API.MessingAPI;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.MessageItemAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.MessageListItemAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.MessingAccountAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageAccountActivity extends AppCompatActivity {
    SharedPreferences sharedPreferencesProfile;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    RecyclerView recyclerContact,recyclerMessage;
    List<ModelString> ContacList,MessageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_message_account);
        recyclerContact = findViewById(R.id.rcv_message_avata);
        recyclerMessage = findViewById(R.id.rcv_message_item);
       // sharedPreferencesProfile = getSharedPreferences("profilepref", MODE_PRIVATE);
        ShowlistChanel();
        showContact();
    }
        ConfigData data = new ConfigData();
    private  void ShowlistChanel(){
        String email = data.userId(MessageAccountActivity.this);
        MessingAPI.api.getMessageList(email).enqueue(new Callback<List<ModelString>>() {

            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                if(response.isSuccessful()){
                    MessageList = response.body();
                    //Toast.makeText(MessageAccountActivity.this, ContacList.toString(), Toast.LENGTH_SHORT).show();
                    MessageListItemAdapter messingAccountAdapter = new MessageListItemAdapter(MessageList,MessageAccountActivity.this);
                    context = getApplicationContext();
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerMessage.setLayoutManager(mLayoutManager);
                    recyclerMessage.setItemAnimator(new DefaultItemAnimator());
                    recyclerMessage.setAdapter(messingAccountAdapter);


                }else {
                    Toast.makeText(MessageAccountActivity.this, "faill!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {
                Toast.makeText(MessageAccountActivity.this, "Connect error, unable to find classes!", Toast.LENGTH_SHORT).show();
            }
        });
        refresh(2000);
    }
    private void showContact(){
//        sharedPreferencesProfile = getSharedPreferences("login", MODE_PRIVATE);
//        String email = sharedPreferencesProfile.getString("user",null);
        String email = data.userId(MessageAccountActivity.this);
        MessingAPI.api.getContact(email).enqueue(new Callback<List<ModelString>>() {

            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                if(response.isSuccessful()){
                    ContacList = response.body();
                    //Toast.makeText(MessageAccountActivity.this, ContacList.toString(), Toast.LENGTH_SHORT).show();
                    MessingAccountAdapter messingAccountAdapter = new MessingAccountAdapter(ContacList,MessageAccountActivity.this);
                    context = getApplicationContext();
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerContact.setLayoutManager(mLayoutManager);
                    recyclerContact.setItemAnimator(new DefaultItemAnimator());
                    recyclerContact.setAdapter(messingAccountAdapter);
                }else {
                    Toast.makeText(MessageAccountActivity.this, "faill!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {
                Toast.makeText(MessageAccountActivity.this, "Connect error, unable to find classes!", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void refresh(int miliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ShowlistChanel();
            }
        };
        handler.postDelayed(runnable,miliseconds);
    }
}