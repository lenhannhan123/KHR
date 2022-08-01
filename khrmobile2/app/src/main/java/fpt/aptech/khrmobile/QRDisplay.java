package fpt.aptech.khrmobile;

import static fpt.aptech.khrmobile.MainActivity.profilePreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRDisplay extends AppCompatActivity {
    private ImageView qrImage;
    private Bitmap bitmap;
    private TextView tvQRName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrdisplay);
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.profilePreferences, Context.MODE_PRIVATE);
        String nameKey = sharedpreferences.getString(MainActivity.Name, null);
        String codeKey = sharedpreferences.getString(MainActivity.Code, null);

        tvQRName = findViewById(R.id.tvQRName);
        qrImage = findViewById(R.id.qr_image);
        tvQRName.setText(nameKey);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(codeKey, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrImage.setImageBitmap(bitmap);
        }catch (Exception e){

        }
    }
}