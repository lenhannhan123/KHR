package fpt.aptech.khrmobile;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.Entities.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassPage extends AppCompatActivity {

    TextView tvUsername;
    TextView tvRecoveryCode;
    TextView tvNewPass;
    TextView tvNewPassConfirm;
    TextView btnSendCode;
    Button btnSubmit;
    private long pressTimer = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_page);
        getSupportActionBar().hide();

        tvUsername = findViewById(R.id.Forgetpass_txtUsername);
        tvRecoveryCode = findViewById(R.id.Forgetpass_txtCode);
        tvNewPass = findViewById(R.id.Forgetpass_txtNewPass);
        tvNewPassConfirm = findViewById(R.id.Forgetpass_txtNewPassConfirm);


        btnSendCode = findViewById(R.id.btnSendCode);
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // spam prevention, using threshold of 10000 ms or 10 seconds
                if (SystemClock.elapsedRealtime() - pressTimer < 10000){
                    String message="Please wait at least 10 seconds..";
                    Toast.makeText(ForgetPassPage.this,message,Toast.LENGTH_SHORT).show();
                    return;
                }
                pressTimer = SystemClock.elapsedRealtime();
                Account account = new Account();
                account.setMail(tvUsername.getText().toString());
                sendRecoverCodeSms(account);
            }
        });

        btnSubmit = findViewById(R.id.Forgetpass_btnSend);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(tvUsername.getText().toString())
                        || TextUtils.isEmpty(tvRecoveryCode.getText().toString())
                        || TextUtils.isEmpty(tvNewPass.getText().toString())
                        || TextUtils.isEmpty(tvNewPassConfirm.getText().toString())){
                    String message = "All inputs required ...";
                    Toast.makeText(ForgetPassPage.this, message, Toast.LENGTH_SHORT).show();
                }
                Account account = new Account();
                account.setMail(tvUsername.getText().toString());
                account.setRecoverycode(tvRecoveryCode.getText().toString());
                account.setPassword(tvNewPassConfirm.getText().toString());
                recoveryChangePass(account);
            }
        });
    }

    private void sendRecoverCodeSms(Account account){
        Call<Account> call = ApiClient.getService().sendRecoverCodeSms(account);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    String message="Recovery code sent!";
                    Toast.makeText(ForgetPassPage.this,message,Toast.LENGTH_SHORT).show();
                }
                else{
                    String message="An error occured, please try again later..";
                    Toast.makeText(ForgetPassPage.this,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                String message="An error occured, please try again later..";
                Toast.makeText(ForgetPassPage.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void recoveryChangePass(Account account){
        if(!tvNewPass.getText().toString().equals(tvNewPassConfirm.getText().toString())){
            String message="Password does not match..";
            Toast.makeText(ForgetPassPage.this,message,Toast.LENGTH_SHORT).show();
        }
        else{
            Call<Account> call = ApiClient.getService().recoveryChangePass(account);
            call.enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    if(response.isSuccessful()){
                        String message="New password saved!";
                        Toast.makeText(ForgetPassPage.this,message,Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String message="Incorrect recovery code.. ";
                        Toast.makeText(ForgetPassPage.this,message,Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    String message="An error occured, please try again later..";
                    Toast.makeText(ForgetPassPage.this,message,Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}