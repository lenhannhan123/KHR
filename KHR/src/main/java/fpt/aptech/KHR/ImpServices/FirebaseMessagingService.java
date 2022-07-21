///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package fpt.aptech.KHR.ImpServices;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author LÊ HỮU TÂM
// */
//@Service
//public class FirebaseMessagingService {
//
//    private final FirebaseMessaging firebaseMessaging;
//
//    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
//        this.firebaseMessaging = firebaseMessaging;
//    }
//    public String sendNotification(String token, String title, String body) throws FirebaseMessagingException {
//        Notification notification = Notification
//                .builder()
//                .setTitle(title)
//                .setBody(body)
//                .build();
//        Message message = Message
//                .builder()
//                .setToken(token)
//                .setNotification(notification)
//                .build();
//        return firebaseMessaging.send(message);
//    }
//    public String sendAllNotification(String token, String title, String body) throws FirebaseMessagingException {
//        Notification notification = Notification
//                .builder()
//                .setTitle(title)
//                .setBody(body)
//                .build();
//        Message message = Message
//                .builder()
//                .setToken(token)
//                .setNotification(notification)
//                .build();
//        // firebaseMessaging.
//        return firebaseMessaging.send(message);
//    }
//}
