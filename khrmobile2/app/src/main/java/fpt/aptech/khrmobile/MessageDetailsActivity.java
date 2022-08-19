package fpt.aptech.khrmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Time;
import java.time.Duration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fpt.aptech.khrmobile.API.MessingAPI;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.MessageItemAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.MessageListItemAdapter;
import fpt.aptech.khrmobile.ListBaseAdapter.MessingAccountAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageDetailsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferencesProfile;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String idSelect,name,avatar;
    Context context;
    RecyclerView recyclerMessage;
    List<ModelString> MessageList;
    TextView view;
    ImageView imageView;

    ConfigData data = new ConfigData();
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        email = data.userId(MessageDetailsActivity.this);
        avatar  = intent.getStringExtra("avatar");
        name  = intent.getStringExtra("name");
        idSelect  = intent.getStringExtra("idSelect");
        recyclerMessage = findViewById(R.id.rcv_message_item_details);
        view = findViewById(R.id.tvAccname);
        imageView = findViewById(R.id.imgLMavatar);
        loadpage(name,avatar);
        getMessage();
        sendMessage();
    }
    private void getMessage(){
        sharedPreferencesProfile = getSharedPreferences("login", MODE_PRIVATE);

        MessingAPI.api.getMessage(email,idSelect).enqueue(new Callback<List<ModelString>>() {

            @Override
            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                if(response.isSuccessful()){
                    MessageList = response.body();
                    //Toast.makeText(MessageAccountActivity.this, ContacList.toString(), Toast.LENGTH_SHORT).show();
                    MessageItemAdapter messingAccountAdapter = new MessageItemAdapter(MessageList,MessageDetailsActivity.this);
                    context = getApplicationContext();
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerMessage.setLayoutManager(mLayoutManager);
                    recyclerMessage.setItemAnimator(new DefaultItemAnimator());
                    recyclerMessage.setAdapter(messingAccountAdapter);
                }else {
                    Toast.makeText(MessageDetailsActivity.this, "faill!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ModelString>> call, Throwable t) {
                Toast.makeText(MessageDetailsActivity.this, "Connect error, unable to find classes!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void sendMessage(){
        ImageButton button = findViewById(R.id.btn_sent);
        TextView textView =findViewById(R.id.tvcontentmess);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessingAPI.api.SendMessage(textView.getText().toString(),email,idSelect).enqueue(new Callback<List<ModelString>>() {

                    @Override
                    public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
                        if(response.isSuccessful()){
                            getMessage();
                            textView.setText("");

                        }else {
                            Toast.makeText(MessageDetailsActivity.this, "faill!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<ModelString>> call, Throwable t) {
                        Toast.makeText(MessageDetailsActivity.this, "Connect error, unable to find classes!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private void refresh(int miliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getMessage();
            }
        };
        handler.postDelayed(runnable,miliseconds);
    }
    private void loadpage(String name,String avatar){
        view.setText(name);
        Glide.with(MessageDetailsActivity.this)
                .load("http://" + ConfigData.IP + ":7777/api/view-profile-image?filename=" +avatar )
                .override(400, 400)
                .into(imageView);
    }

}