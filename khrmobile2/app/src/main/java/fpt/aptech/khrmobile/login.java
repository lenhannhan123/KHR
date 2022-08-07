package fpt.aptech.khrmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.AccountToken;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import fpt.aptech.khrmobile.Services.TokenServices;
import fpt.aptech.khrmobile.ServicesImp.TokenUtilAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView btnGoogle;

    EditText Login_txtUsername;
    EditText Login_txtPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String USERNAME_KEY = "user";
    String PASSWORD_KEY = "password";
    TokenServices tokenServices;
    void openFormForget(){
        TextView linkForget = findViewById(R.id.btnSendCode);
        linkForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, ForgetPassPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void signInGoogle(){
        btnGoogle = findViewById(R.id.google_btn);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        navigateToSecondActivity();
                    }
                }
            }
    );


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1000){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                task.getResult(ApiException.class);
//
//            }catch (ApiException e){
//                Toast.makeText(getApplicationContext(),"Lỗi ApiException",Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void navigateToSecondActivity() {

        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        finish();
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
                    String user = Login_txtUsername.getText().toString().trim();
                    String password = Login_txtPassword.getText().toString().trim();
                    editor = sharedPreferences.edit();
                    editor.putString(USERNAME_KEY, Login_txtUsername.getText().toString().trim());
                    editor.putString(PASSWORD_KEY, Login_txtPassword.getText().toString().trim());
                    editor.commit();

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
        tokenServices = TokenUtilAPI.getTokenServices();
        openFormForget();
        login();
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        Login_txtUsername.setText(sharedPreferences.getString(USERNAME_KEY,""));
        Login_txtPassword.setText(sharedPreferences.getString(PASSWORD_KEY,""));

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        signInGoogle();

    }

    public void loginUser(LoginRequest loginRequest){
        Call<Account> accountCall = ApiClient.getService().loginUser(loginRequest);
        accountCall.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account = response.body();
                    sendRegistrationToServer(account);
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
    private void sendRegistrationToServer(Account account) {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                AccountToken tk = new AccountToken();
                tk.setToken(task.getResult());
                tk.setMail(account);
                tokenServices.AddToken(tk).enqueue(new Callback<AccountToken>() {
                    @Override
                    public void onResponse(Call<AccountToken> call, Response<AccountToken> response) {
                        System.out.println(account.getMail());
                        System.out.println("Thành công");
                    }

                    @Override
                    public void onFailure(Call<AccountToken> call, Throwable t) {
                        System.out.println("Thất bại");
                    }
                });

            }
        });
    }

}