package keysight.ixia.hackathon.ixride_server.controller;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import keysight.ixia.hackathon.ixride_server.notification.AndroidPushNotificationsService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NotificationController {

    private final String TOPIC = "IxRide";

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;


    @GetMapping("/sendnotification")
    public ResponseEntity<String> send() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");
        body.put("show_in_foreground", true);

        JSONObject notification = new JSONObject();
        notification.put("title", "IxRide Notification");
        notification.put("body", "A new route has been calculated!");


        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

        body.put("notification", notification);
        body.put("data", data);
        body.put("ttl", 3600);


        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
