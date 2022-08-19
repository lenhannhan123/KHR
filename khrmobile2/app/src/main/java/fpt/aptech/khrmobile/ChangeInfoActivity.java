package fpt.aptech.khrmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.Entities.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeInfoActivity extends AppCompatActivity {
    MaterialTextView tvDate;
    MaterialButton btnPickDate;
    EditText editTextFullName;
    EditText editTextPhone;
    Spinner spnCategory;
    Button btnUpdateProfile;
    boolean convertGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_change_infor_layout);

        BottomNavigationView bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        CallNav callNav = new CallNav();
        callNav.call(bottom_navigation,R.id.page_3,ChangeInfoActivity.this);

        ScrollView scrollView = findViewById(R.id.scrollView);
        callNav.setDisplay(scrollView,ChangeInfoActivity.this,0.8);


        editTextFullName = findViewById(R.id.Info_txtMail);
        editTextPhone = findViewById(R.id.Info_txtPhone);
        tvDate = findViewById(R.id.tv_Date);
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.profilePreferences, Context.MODE_PRIVATE);
        String namekey = sharedpreferences.getString(MainActivity.Name,null);
        String mailkey = sharedpreferences.getString(MainActivity.Mail,null);
        String phonekey = sharedpreferences.getString(MainActivity.Phone,null);
        String birthdaykey = sharedpreferences.getString(MainActivity.Birthday,null);
        String genderkey = sharedpreferences.getString(MainActivity.Gender, null);

        editTextFullName.setText(namekey);
        editTextPhone.setText(phonekey);
        tvDate.setText(birthdaykey);


        pickDate();
        buttonBack();
        ChangeSpinner();


        btnUpdateProfile = findViewById(R.id.BtnUpdateProfile);
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = new Account();

                Spinner spinner = (Spinner)findViewById(R.id.Info_spinner_gender);
                String gender = spinner.getSelectedItem().toString();

                if (gender == "Nam"){
                    convertGender = true;
                }
                else if (gender == "Nữ"){
                    convertGender = false;
                }
                account.setFullname(editTextFullName.getText().toString());
                account.setPhone(editTextPhone.getText().toString());
                account.setBirthdate(tvDate.getText().toString());
                account.setGender(convertGender);
                account.setMail(mailkey);
                submitUpdate(account);
            }
        });


    }

    private void submitUpdate(Account account){
        Spinner spinner = (Spinner)findViewById(R.id.Info_spinner_gender);

        if(editTextFullName.getText().toString().isEmpty()
                || editTextPhone.getText().toString().isEmpty()
                || tvDate.getText().toString().isEmpty()
                || spinner.getSelectedItem().toString().isEmpty()
        ){
            String message="Item must not be empty..";
            Toast.makeText(ChangeInfoActivity.this,message,Toast.LENGTH_SHORT).show();
        }
        else{
            Call<Account> call = ApiClient.getService().changeProfileInfo(account);
            call.enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    if(response.isSuccessful()){
                        Intent intent = new Intent(ChangeInfoActivity.this, MainAccountActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{

                        String message="Input is invalid.. ";
                        Toast.makeText(ChangeInfoActivity.this,message,Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    String message="An error occured, please try again later..";
                    Toast.makeText(ChangeInfoActivity.this,message,Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void pickDate(){
        btnPickDate = findViewById(R.id.btn_pickDate);
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }


        });
    }

    private void showDatePickerDialog() {
        tvDate = findViewById(R.id.tv_Date);
        MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selection);
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate  = formatDate.format(calendar.getTime());
                tvDate.setText(formattedDate);
            }
        });
        materialDatePicker.show(getSupportFragmentManager(),"TAG");
    }


    private void ChangeSpinner(){
        spnCategory = (Spinner) findViewById(R.id.Info_spinner_gender);
        List<String> list = new ArrayList<>();
        list.add("Nam");
        list.add("Nữ");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnCategory.setAdapter(adapter);

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.profilePreferences, Context.MODE_PRIVATE);
        String genderkey = sharedpreferences.getString(MainActivity.Gender, null);
        if(genderkey == "true"){
            spnCategory.setSelection(0);
        }
        else if(genderkey == "false"){
            spnCategory.setSelection(1);
        }


        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void buttonBack(){
        ImageButton button = findViewById(R.id.btn_change_infor_back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeInfoActivity.this, MenuChangeAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}