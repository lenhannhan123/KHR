package fpt.aptech.khrmobile;

import android.app.Activity;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.AccountToken;
import fpt.aptech.khrmobile.Entities.LoginRequest;
import fpt.aptech.khrmobile.Entities.ModelString;
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
                        navigateToLogin();
                    }
                }
            }
    );

    private void navigateToLogin() {
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
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

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);
        signInGoogle();

    }

    public void loginUser(LoginRequest loginRequest){

        ApiClient.getService().LoginAPI(loginRequest.getMail(),loginRequest.getPassword()).enqueue(new Callback<ModelString>() {
            @Override
            public void onResponse(Call<ModelString> call, Response<ModelString> response) {
                ModelString data = response.body();
                Toast.makeText(login.this, ""+data.getData1(), Toast.LENGTH_SHORT).show();

                if(data.getData1().toString().equals("Done")){

                    Call<ModelString> accountCall = ApiClient.getService().getProfileInfo(data.getData2());
                    accountCall.enqueue(new Callback<ModelString>() {
                        @Override
                        public void onResponse(Call<ModelString> call, Response<ModelString> response) {
                            ModelString modelString = response.body();
                            Account account = new Account();
                            account.setFullname(modelString.getData1());
                            account.setMail(modelString.getData2());
                            account.setPhone(modelString.getData3());
                            account.setBirthdate(modelString.getData4());
                            if(modelString.getData5().equals("true")){
                                account.setGender(true);
                            }
                            else if (modelString.getData5().equals("false")){
                                account.setGender(false);
                            }
                            account.setCode(modelString.getData7());

                            sendRegistrationToServer(account);
                            switch (data.getData4()){

                                case "0":
                                    startActivity(new Intent(login.this, MainActivity.class).putExtra("data",account));
                                    finish();
                                    break;
                                case "2":
                                    startActivity(new Intent(login.this, ScannerActivity.class));
                                    finish();
                                    break;

                            }
                        }

                        @Override
                        public void onFailure(Call<ModelString> call, Throwable t) {

                        }
                    });




                }

            }

            @Override
            public void onFailure(Call<ModelString> call, Throwable t) {
                Toast.makeText(login.this,"Lỗi mạng",Toast.LENGTH_SHORT).show();
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