package fpt.aptech.khrmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.Result;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fpt.aptech.khrmobile.API.TimekeepingService;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.Timekeeping;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScannerActivity extends AppCompatActivity {
    TimekeepingService service;
    private CodeScanner mCodeScanner;
    public static final String profilePreferences = "profilepref";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        sharedPreferences = getSharedPreferences(profilePreferences, Context.MODE_PRIVATE);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
//        mCodeScanner.setCamera(1);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hashHandle(result.getText());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void hashHandle(String hash){
        final Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://" +  ConfigData.IP + ":7777/").
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        service = retrofit.create(TimekeepingService.class);
        Call<List<Account>> call = service.accountList();
        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if(response.isSuccessful()){
                    List<Account> accountList = response.body();
                    String bcryptHashString = BCrypt.withDefaults().hashToString(12, hash.toCharArray());
                    for (Account account: accountList) {
                        BCrypt.Result _result = BCrypt.verifyer().verify(account.getCode().toCharArray(), bcryptHashString);
                        if(_result.verified){
                            Timekeeping timekeeping = new Timekeeping();
                            String _hash = account.getMail();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
//                            builder.setTitle("Information");
//                            builder.setMessage(_hash);
//                            builder.show();
                            checkin(new Timekeeping(), _hash);
                            //checkout(timekeeping, _hash);
                        }
                    }
                }else{
                    Toast.makeText(ScannerActivity.this, "Kết nối server thất bại!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public class NullOnEmptyConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }

    private void checkin(Timekeeping timekeeping, String mail){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://" +  ConfigData.IP + ":7777/").
                addConverterFactory(new NullOnEmptyConverterFactory()).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        service = retrofit.create(TimekeepingService.class);
        Call<Timekeeping> call = service.checkin(timekeeping, mail);
        call.enqueue(new Callback<Timekeeping>() {
            @Override
            public void onResponse(Call<Timekeeping> call, Response<Timekeeping> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ScannerActivity.this, "Điểm danh thành công!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ScannerActivity.this, "Không tìm thấy ca của bạn!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Timekeeping> call, Throwable t) {
                Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkout(Timekeeping timekeeping, String mail) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://" +  ConfigData.IP + ":7777/").
                addConverterFactory(new NullOnEmptyConverterFactory()).
                addConverterFactory(GsonConverterFactory.create(gson)).
                build();
        service = retrofit.create(TimekeepingService.class);
        Call<Timekeeping> call = service.checkout(timekeeping, mail);
        call.enqueue(new Callback<Timekeeping>() {
            @Override
            public void onResponse(Call<Timekeeping> call, Response<Timekeeping> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ScannerActivity.this, "Điểm danh thành công!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ScannerActivity.this, "Không tìm thấy ca của bạn!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Timekeeping> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                builder.setTitle("Error");
                builder.setMessage(t.getMessage());
                builder.show();
                //Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}