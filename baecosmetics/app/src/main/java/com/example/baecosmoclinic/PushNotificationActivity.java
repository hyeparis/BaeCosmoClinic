package com.example.baecosmoclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.douglas.bean.NotificationList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.baecosmoclinic.AppChannel.CHANNEL_1_ID;

public class PushNotificationActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private EditText title;
    private EditText desc;


    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        title = findViewById(R.id.notificationTitle);
        desc = findViewById(R.id.notificationDesc);

        db = FirebaseDatabase.getInstance().getReference("notification");


    }


    public void sendNotication(View view) {

        String eTitle = title.getText().toString();
        String eDesc = desc.getText().toString();

        saveToDatabase(eTitle, eDesc);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle(eTitle)
                .setContentText(eDesc)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void saveToDatabase(String title, String desc) {

        NotificationList notificationList = new NotificationList(title, desc);
        String id = db.push().getKey();
        db.child(id).setValue(notificationList);

        Toast.makeText(this, "Notification saved", Toast.LENGTH_SHORT).show();

    }
}
