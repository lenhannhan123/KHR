package fpt.aptech.KHR;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.twilio.Twilio;
import fpt.aptech.KHR.ImpServices.SmsService;
import static fpt.aptech.KHR.ImpServices.SmsService.ACCOUNT_SID;
import static fpt.aptech.KHR.ImpServices.SmsService.AUTH_TOKEN;
import java.io.IOException;
import java.security.SecureRandom;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication  
public class KhrApplication {
    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("khr-app-10543-firebase-adminsdk-mbjiy-ccaae4a813.json").getInputStream());
        SecureRandom random = new SecureRandom();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(googleCredentials)
                .build();
        String name = String.valueOf(random.nextInt()); 
        FirebaseApp app = FirebaseApp.initializeApp(options,name);
        return FirebaseMessaging.getInstance(app);
    }
    public static void main(String[] args) {
        SpringApplication.run(KhrApplication.class, args);
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
