package fpt.aptech.khrmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ForgetPassPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_page);
        getSupportActionBar().hide();
    }
}