package fpt.aptech.KHR;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class KhrApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhrApplication.class, args);
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
    
//    @Bean
//    public FirebaseMessaging firebaseMessaging() throws IOException {
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(new ClassPathResource("khr-app-10543-firebase-adminsdk-mbjiy-5b55aedc9e.json").getInputStream());
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(googleCredentials)
//                .build();
//        FirebaseApp app = FirebaseApp.initializeApp(options);
//        return FirebaseMessaging.getInstance(app);
//    }

    
}


