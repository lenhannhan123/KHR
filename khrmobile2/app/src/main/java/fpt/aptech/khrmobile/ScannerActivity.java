package fpt.aptech.khrmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.API.TimekeepingService;
import fpt.aptech.khrmobile.Config.ConfigData;
import fpt.aptech.khrmobile.Entities.Timekeeping;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScannerActivity extends AppCompatActivity {
    TimekeepingService service;
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setCamera(1);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Retrofit retrofit = new Retrofit.Builder().
                                baseUrl("http://192.168.1.23:7777/").
                                addConverterFactory(GsonConverterFactory.create()).
                                build();
                        service = retrofit.create(TimekeepingService.class);
                        Timekeeping timekeeping = new Timekeeping();
                        Call<Timekeeping> call = service.checkout(timekeeping);
                        call.enqueue(new Callback<Timekeeping>() {
                            @Override
                            public void onResponse(Call<Timekeeping> call, Response<Timekeeping> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ScannerActivity.this, "Checkin thành công!", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(response.toString() + " " + call.toString());
                                    builder.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Timekeeping> call, Throwable t) {
                                Log.e("Error: ", t.getMessage());
                                Toast.makeText(ScannerActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        Toast.makeText(ScannerActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
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
}