///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package fpt.aptech.KHR.ImpServices;
//
//import com.google.api.core.ApiFuture;
//import com.google.firebase.messaging.BatchResponse;
//import com.google.firebase.messaging.FcmOptions;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.MulticastMessage;
//import com.google.firebase.messaging.Notification;
//import com.google.firebase.messaging.TopicManagementResponse;
//import com.google.firebase.messaging.WebpushConfig;
//import com.google.firebase.messaging.WebpushFcmOptions;
//import com.google.firebase.messaging.WebpushNotification;
//import java.util.Arrays;
//import java.util.List;
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
//    public BatchResponse sendNotification(List<String> tokenList, fpt.aptech.KHR.Entities.Notification notifica) throws FirebaseMessagingException {
//        Notification notification = Notification
//                .builder()
//                .setTitle(notifica.getTitle())
//                .setBody(notifica.getContent())
//                .build();
//                MulticastMessage message = MulticastMessage
//                .builder()
//                .addAllTokens(tokenList)
//                .setNotification(notification)
//                .build();
//        return firebaseMessaging.sendMulticast(message);
//    }
//
//    public String sendOneNotification(fpt.aptech.KHR.Entities.AccountNotification contentNotification) throws FirebaseMessagingException {
//        Notification notification = Notification
//                .builder()
//                .setTitle(contentNotification.getIdnotification().getTitle())
//                .setBody(contentNotification.getIdnotification().getContent())
//                .build();
//        Message message = Message
//                .builder()
//                ///lay token
//                .setToken(contentNotification.getMail().getMail())
//                .setNotification(notification)
//                .build();
//        // firebaseMessaging.
//        return firebaseMessaging.send(message);
//    }
//        public ApiFuture<BatchResponse> sendNotificationinFuture(List<String> tokenList, fpt.aptech.KHR.Entities.Notification notifica) throws FirebaseMessagingException {
//        Notification notification = Notification
//                .builder()
//                .setTitle(notifica.getTitle())
//                .setBody(notifica.getContent())
//                .build();
//                MulticastMessage message = MulticastMessage
//                .builder()
//                .addAllTokens(tokenList)
//                .setNotification(notification)
//                .build();
//        return firebaseMessaging.sendMulticastAsync(message);
//    }
//
//    public void CreateTopic(fpt.aptech.KHR.Entities.Notification contentNotification, List<String> listToken) throws FirebaseMessagingException {
//        // These registration tokens come from the client FCM SDKs.
//
//// Unsubscribe the devices corresponding to the registration tokens from
//// the topic.
//        TopicManagementResponse response = firebaseMessaging.unsubscribeFromTopic(
//                listToken, contentNotification.getTitle());
//// See the TopicManagementResponse reference documentation
//// for the contents of response.
//        response.getSuccessCount();
//    }
//        public BatchResponse sendMorePeople(fpt.aptech.KHR.Entities.AccountNotification contentNotification,List<String> listToken) throws FirebaseMessagingException {
//        Notification notification = Notification
//                .builder()
//                .setTitle(contentNotification.getIdnotification().getTitle())
//                .setBody(contentNotification.getIdnotification().getContent())
//                .build();
//        MulticastMessage message = MulticastMessage
//                .builder()
//                ///lay token
//                .addAllTokens(listToken)
//                .setNotification(notification)
//                .build();
//        // firebaseMessaging.
//        return firebaseMessaging.sendMulticast(message);
//    }
//        public BatchResponse sendAllPeople(fpt.aptech.KHR.Entities.Notification contentNotification,List<String> listToken) throws FirebaseMessagingException {
//        Notification notification = Notification
//                .builder()
//                .setTitle(contentNotification.getTitle())
//                .setBody(contentNotification.getContent())
//                .build();
//        MulticastMessage message = MulticastMessage
//                .builder()
//                ///lay token
//                .addAllTokens(listToken)
//                .setNotification(notification)
//                .build();
//        // firebaseMessaging.
//        return firebaseMessaging.sendMulticast(message);
//    }
//}
