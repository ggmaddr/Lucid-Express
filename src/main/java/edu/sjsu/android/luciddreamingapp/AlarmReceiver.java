package edu.sjsu.android.luciddreamingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {
    Ringtone ringtone;
    RingtoneManager manager;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getStringExtra("isCancelled"), "Yes")) {
            // Cancel the alarm
            if (ringtone != null) {
                ringtone.stop();
                Log.d("AlarmReceiver", "Ringtone stopped");
            }
            if (manager != null) {
                manager.stopPreviousRingtone();
            }

        } else {
            manager = new RingtoneManager(context);
            Toast.makeText(context, "Your Alarm Ringing", Toast.LENGTH_LONG).show();
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            // setting default ringtone
            ringtone = RingtoneManager.getRingtone(context, alarmUri);
            // play ringtone
            ringtone.play();
            Log.d("AlarmReceiver", "RingTone played");
        }
    }
}