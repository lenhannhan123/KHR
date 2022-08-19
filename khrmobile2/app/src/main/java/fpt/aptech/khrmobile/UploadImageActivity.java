package fpt.aptech.khrmobile;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import fpt.aptech.khrmobile.API.ApiClient;
import fpt.aptech.khrmobile.Entities.Account;
import fpt.aptech.khrmobile.Entities.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity {
    public static final String TAG = UploadImageActivity.class.getName();
    public static final int MY_REQUEST_CODE = 10;
    ImageView ivChoosePhoto;
    Button btnUploadPhoto;
    Uri mUri;
    private ProgressDialog progressDialog;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if(result.getResultCode()== Activity.RESULT_OK){
                        //There are no more request code
                        Intent data = result.getData();
                        if(data==null){
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            ivChoosePhoto.setImageBitmap(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        initUI();
        //init Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        ivChoosePhoto.setOnClickListener(view -> {
            onClickRequestPermission();
        });

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUri != null){
                    callApiUpdatePhoto();
                }
            }
        });

    }

    private void callApiUpdatePhoto(){
        progressDialog.show();
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.profilePreferences, Context.MODE_PRIVATE);
        String mailkey = sharedpreferences.getString(MainActivity.Mail,null);
        RequestBody requestBodyMail = RequestBody.create(MediaType.parse("multipart/form-data"), mailkey);

        String strRealPath = RealPathUtil.getRealPath(this,mUri);

        Log.e("TAG", "--->" + strRealPath);
        File file = new File(strRealPath);
        RequestBody requestAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData(Account.Key_avatar, file.getName(), requestAvt);

        Call<Account> call = ApiClient.getService().uploadImage(multipartBodyAvt, requestBodyMail);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                progressDialog.dismiss();
                Account account = response.body();
                if(account != null){
                    Glide.with(UploadImageActivity.this).load(account.getAvatar()).into(ivChoosePhoto);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UploadImageActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickRequestPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();

        }else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==MY_REQUEST_CODE){
            if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }

    }

    private void initUI(){
        ivChoosePhoto = findViewById(R.id.ivChoosePhoto);
        btnUploadPhoto = findViewById(R.id.btnUploadImage);

    }



}