package fpt.aptech.khrmobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    EditText Login_txtUsername;
    EditText Login_txtPassword;

    void openFormForget(){
        TextView linkforget = findViewById(R.id.Login_linkforrgetpass);
        linkforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, ForgetPassPage.class);
                startActivity(intent);
            }
        });
    }


    void login(){
        Button Login_button = findViewById(R.id.Login_btnlogin);
        Login_txtUsername = findViewById(R.id.Login_txtUsername);
        Login_txtPassword = findViewById(R.id.Login_txtPassword);

        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Login_txtUsername.getText().toString()) || TextUtils.isEmpty(Login_txtPassword.getText().toString())){
                    String message = "All inputs required ...";
                    Toast.makeText(login.this, message, Toast.LENGTH_SHORT).show();
                }
                else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setMail(Login_txtUsername.getText().toString());
                    loginRequest.setPassword(Login_txtPassword.getText().toString());
                    loginUser(loginRequest);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        openFormForget();
        login();
    }

    public void loginUser(LoginRequest loginRequest){
        Call<Account> accountCall = ApiClient.getService().loginUser(loginRequest);
        accountCall.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account = response.body();
                    startActivity(new Intent(login.this, MainActivity.class).putExtra("data",account));
                    finish();
                }
                else{
                    String message="An error occured, please try again later..";
                    Toast.makeText(login.this,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(login.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

}