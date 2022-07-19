package fpt.aptech.khrmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        Login_txtPassword = findViewById(R.id.Login_txtUsername);
        Login_txtPassword = findViewById(R.id.Login_txtPassword);

        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
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
}