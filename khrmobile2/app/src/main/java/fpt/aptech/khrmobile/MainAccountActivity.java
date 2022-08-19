package fpt.aptech.khrmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.ModelString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAccountActivity extends AppCompatActivity {
    Account account;
    Context context;
    TextView username;
    TextView mail;
    TextView phone;
    TextView birthday;
    TextView gender;
    ImageView avatar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_main_account_layout);

        username = findViewById(R.id.tvUsername);
        mail = findViewById(R.id.tvMail);
        phone = findViewById(R.id.tvPhone);
        birthday = findViewById(R.id.tvBirthday);
        gender = findViewById(R.id.tvGender);
        avatar = findViewById(R.id.ivAvatarProfile);

        getDetailsViaCallAPI();





        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_3,MainAccountActivity.this);


        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,MainAccountActivity.this,0.8);

        buttonchange();
        buttonOpenQR();

    }

    private void buttonchange(){
        ImageButton button = findViewById(R.id.main_account_button_change);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAccountActivity.this, MenuChangeAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getDetailsViaCallAPI(){
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.profilePreferences, Context.MODE_PRIVATE);
        String mailkey = sharedpreferences.getString(MainActivity.Mail,null);
        Call<ModelString> call = ApiClient.getService().getProfileInfo(mailkey);
        call.enqueue(new Callback<ModelString>() {
            @Override
            public void onResponse(Call<ModelString> call, Response<ModelString> response) {
                if(response.isSuccessful()){
                    ModelString modelString = response.body();
                    username.setText(modelString.getData1());
                    mail.setText(modelString.getData2());
                    phone.setText(modelString.getData3());
                    birthday.setText(modelString.getData4());
                    String genderkey = modelString.getData5();
                    if(genderkey.equals("true")){
                        gender.setText("Nam");
                    }
                    else if(genderkey.equals("false")){
                        gender.setText("Nữ");
                    }

                    context = getApplicationContext();
                    int radius = 500; // corner radius, higher value = more rounded
                    Glide.with(context)
                            .load("http://" + ConfigData.IP + ":7777/api/view-profile-image?filename=" + modelString.getData6())
//                .transform(new RoundedCorners(radius))
                            .transform(new CircleCrop())
                            .override(600, 600)
                            .error(R.drawable.icon5)
                            .into(avatar);
                }
                else{

                    String message="Can not load profile.. ";
                    Toast.makeText(MainAccountActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ModelString> call, Throwable t) {
                String message="An error occured, please try again later..";
                Toast.makeText(MainAccountActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDetailsViaSharePreference(){
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.profilePreferences, Context.MODE_PRIVATE);

        String namekey = sharedpreferences.getString(MainActivity.Name,null);
        String mailkey = sharedpreferences.getString(MainActivity.Mail,null);
        String phonekey = sharedpreferences.getString(MainActivity.Phone,null);
        String birthdaykey = sharedpreferences.getString(MainActivity.Birthday,null);
        String avatarkey = sharedpreferences.getString(MainActivity.Avatar, null);
        String genderkey = sharedpreferences.getString(MainActivity.Gender, null);

        username.setText(namekey);
        mail.setText(mailkey);
        phone.setText(phonekey);
        birthday.setText(birthdaykey);
        if(genderkey == "true"){
            gender.setText("Nam");
        }
        else if(genderkey == "false"){
            gender.setText("Nữ");
        }

        context = getApplicationContext();
        int radius = 500; // corner radius, higher value = more rounded
        Glide.with(context)
                .load("http://" + ConfigData.IP + ":7777/api/view-profile-image?filename=" + avatarkey)
//                .transform(new RoundedCorners(radius))
                .transform(new CircleCrop())
                .override(600, 600)
                .error(R.drawable.icon5)
                .into(avatar);
    }

    private void buttonOpenQR(){
        Button btnOpenQR = findViewById(R.id.btnOpenQR);
        btnOpenQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAccountActivity.this, QRDisplay.class);
                startActivity(intent);
                finish();
            }
        });
    }



}