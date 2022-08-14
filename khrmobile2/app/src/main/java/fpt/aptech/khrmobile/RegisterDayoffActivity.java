package fpt.aptech.khrmobile;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fpt.aptech.khrmobile.Services.DayOffServices;
import fpt.aptech.khrmobile.Services.TokenServices;
import fpt.aptech.khrmobile.ServicesImp.DayOffUtillAPI;
import fpt.aptech.khrmobile.ServicesImp.TokenUtilAPI;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.AccountToken;
import fpt.aptech.khrmobile.Entities.DayOff;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDayoffActivity extends AppCompatActivity {
    EditText etstart,etend,etContent,etmail;
    Button btsummit;
    TokenServices tokenServices;
    DayOffServices dayOffServices;
    DialogInterface.OnClickListener onClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_dayoff);
        etstart = findViewById(R.id.etsStartDate);
        etend = findViewById(R.id.etEndDay);
        etContent = findViewById(R.id.etoffcontent);
        etmail = findViewById(R.id.etoffemail);
        btsummit = findViewById(R.id.btnoffsubmit);
        tokenServices = TokenUtilAPI.getTokenServices();
        dayOffServices = DayOffUtillAPI.getAccountDayoff();
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            etstart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterDayoffActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month = month+1;
                            if(month<10){
                                //String date = year+"-0"+month+"-"+day;
                                if(day<10){
                                    String date = year+"-0"+month+"-0"+day;
                                    etstart.setText(date);
                                }else{
                                    String date = year+"-0"+month+"-"+day;
                                    etstart.setText(date);
                                }

                            }else{
                                if(day<10){
                                    String date = year+"-"+month+"-0"+day;
                                    etstart.setText(date);
                                }else{
                                    String date = year+"-"+month+"-"+day;
                                    etstart.setText(date);
                                }
                            }

                        }

                    },year,month,day);
                    datePickerDialog.show();
                }

            });
        etend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterDayoffActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        if(month<10){
                            //String date = year+"-0"+month+"-"+day;
                            if(day<10){
                                String date = year+"-0"+month+"-0"+day;
                                etend.setText(date);
                            }else{
                                String date = year+"-0"+month+"-"+day;
                                etend.setText(date);
                            }

                        }else{
                            if(day<10){
                                String date = year+"-"+month+"-0"+day;
                                etend.setText(date);
                            }else{
                                String date = year+"-"+month+"-"+day;
                                etend.setText(date);
                            }
                        }

                    }
                },year,month,day);
                datePickerDialog.closeOptionsMenu();
                datePickerDialog.show();
            }

        });
//        onClickListener = new DatePickerDialog.OnDateSetListener(new OnCompleteListener<>()){
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
//               month = month+1;
//               String date = day+"-"+month+"-"+year;
//               etstart.setText(date);
//            }
//        };
        bidingemail();
        btsummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Submit();
                    Intent intent = new Intent(RegisterDayoffActivity.this,DayOffActivity.class);
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void bidingemail(){ FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
          @Override
          public void onComplete(@NonNull Task<String> task) {
              tokenServices.GetToken(task.getResult()).enqueue(new Callback<AccountToken>() {
                  @Override
                  public void onResponse(Call<AccountToken> call, Response<AccountToken> response) {
                      etmail.setText(response.body().getMail().getMail());
                  }

                  @Override
                  public void onFailure(Call<AccountToken> call, Throwable t) {
                      System.out.println("Load Email Faill");
                  }
              });
          }
      });

    }
    private void Submit() throws ParseException {
        DayOff off = new DayOff();
        Account account = new Account();
        account.setMail(etmail.getText().toString());
        off.setMail(account);
        String sdateStart = etstart.getText().toString();
        String sdateEnd = etend.getText().toString();
        Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(sdateStart);
        Date dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(sdateEnd);
        off.setStartdate(dateStart);
        off.setEnddate(dateEnd);
        off.setDaynumber(Numday(dateStart,dateEnd));
        off.setContent(etContent.getText().toString());
        dayOffServices.AddDayOff(off).enqueue(new Callback<DayOff>() {
            @Override
            public void onResponse(Call<DayOff> call, Response<DayOff> response) {
                if (response.isSuccessful()){
                    //Toast.makeText(RegisterDayoffActivity.this,)
                    System.out.println("thanh cong");
                }else {
                    System.out.println("Errro");
                }

            }

            @Override
            public void onFailure(Call<DayOff> call, Throwable t) {
                System.out.println(t);
            }


        });

    }
    private int Numday(Date st,Date end){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(st);
        c2.setTime(end);
        int noDay = (int) ((c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000));
        return noDay;
    }
}