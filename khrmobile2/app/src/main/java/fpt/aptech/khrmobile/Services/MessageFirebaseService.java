package fpt.aptech.khrmobile.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import fpt.aptech.khrmobile.API.MessingAPI;
import fpt.aptech.khrmobile.Entities.AccountToken;
import fpt.aptech.khrmobile.Entities.ModelString;
import fpt.aptech.khrmobile.ListBaseAdapter.MessageItemAdapter;
import fpt.aptech.khrmobile.MessageDetailsActivity;
import fpt.aptech.khrmobile.NotificationActivity;
import fpt.aptech.khrmobile.R;
import fpt.aptech.khrmobile.ServicesImp.TokenUtilAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageFirebaseService extends FirebaseMessagingService {
    TokenServices tokenServices;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() != null) {
            String title = message.getNotification().getTitle();
            String body = message.getNotification().getBody();
            System.out.println( title);
            System.out.println( body);
            sendNotification(title, body);
        }
    }
    private void sendNotification(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, /* Request code */intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.string_default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(messageTitle)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
//    private void getMessage(){
//        sharedPreferencesProfile = getSharedPreferences("login", MODE_PRIVATE);
//
//        MessingAPI.api.getMessage(email,idSelect).enqueue(new Callback<List<ModelString>>() {
//
//            @Override
//            public void onResponse(Call<List<ModelString>> call, Response<List<ModelString>> response) {
//                if(response.isSuccessful()){
//                    MessageList = response.body();
//                    //Toast.makeText(MessageAccountActivity.this, ContacList.toString(), Toast.LENGTH_SHORT).show();
//                    MessageItemAdapter messingAccountAdapter = new MessageItemAdapter(MessageList, MessageDetailsActivity.this);
//                    context = getApplicationContext();
//                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                    recyclerMessage.setLayoutManager(mLayoutManager);
//                    recyclerMessage.setItemAnimator(new DefaultItemAnimator());
//                    recyclerMessage.setAdapter(messingAccountAdapter);
//                }else {
//                    Toast.makeText(MessageDetailsActivity.this, "faill!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<ModelString>> call, Throwable t) {
//                Toast.makeText(MessageDetailsActivity.this, "Connect error, unable to find classes!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onNewToken(@NonNull String token) {
        tokenServices = TokenUtilAPI.getTokenServices();
        sendRegistrationToServer(token);

    }
    private void sendRegistrationToServer(String token) {
        AccountToken tk = new AccountToken();
        tk.setToken(token);
        tk.setMail(null);
        tokenServices.AddToken(tk).enqueue(new Callback<AccountToken>() {
            @Override
            public void onResponse(Call<AccountToken> call, Response<AccountToken> response) {
                System.out.println("thanh cong");
            }

            @Override
            public void onFailure(Call<AccountToken> call, Throwable t) {
                System.out.println("Thất bại");
            }
        });
    }
}