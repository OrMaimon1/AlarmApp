package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText time;
    private EditText text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

    }

    private void initView() {
        time = findViewById(R.id.editText1);
        text = findViewById(R.id.editText2);
        button = findViewById(R.id.button);
    }
    private void initListener() {
        button.setOnClickListener(v -> {
            String timeInSec = time.getText().toString();
            String textOfAlart = text.getText().toString();
            if (!timeInSec.isEmpty()) {
                int timeInSeconds = Integer.parseInt(timeInSec);
                scheduleAlarm(textOfAlart,timeInSeconds);
            } else
                Toast.makeText(this, "Please enter a time", Toast.LENGTH_SHORT).show();
        });
    }

    private void scheduleAlarm(String text , int timeInSecond) {

        try {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("text",text);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            long triggerAtMillis = System.currentTimeMillis() + (timeInSecond * 1000);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

            Toast.makeText(this, "Alarm set for " + timeInSecond + " seconds", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "Permission to schedule exact alarms is not granted.", Toast.LENGTH_SHORT).show();
        }

    }
}
