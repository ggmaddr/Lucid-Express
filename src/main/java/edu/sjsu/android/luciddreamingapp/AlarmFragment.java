package edu.sjsu.android.luciddreamingapp;


import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlarmFragment extends Fragment {
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String ALARM_STATE_KEY = "alarmState";
    private static final String ALARM_HOUR_KEY = "alarmHour";
    private static final String ALARM_MINUTE_KEY = "alarmMinute";

    private SharedPreferences settings;

    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    Intent intent;
    AlarmReceiver alarmReceiver;

    FragmentActivity activity;

    public AlarmFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        assert activity != null;
        settings = activity.getSharedPreferences(PREFS_NAME, 0);

    }

    @SuppressLint({"NewApi", "ShortAlarm"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        ToggleButton alarmToggle = view.findViewById(R.id.toggleButton);
        alarmTimePicker = view.findViewById(R.id.timePicker);
        alarmReceiver = new AlarmReceiver();
        alarmManager = (AlarmManager) activity.getSystemService(ALARM_SERVICE);

        boolean alarmState = settings.getBoolean(ALARM_STATE_KEY, false);
        alarmToggle.setChecked(alarmState);

        // Restore the PendingIntent if it exists
        if (alarmState) {
            // Get the hour and minute from SharedPreferences
            int hour = settings.getInt(ALARM_HOUR_KEY, 0);
            int minute = settings.getInt(ALARM_MINUTE_KEY, 0);

            // Set the time picker to the saved hour and minute
            alarmTimePicker.setHour(hour);
            alarmTimePicker.setMinute(minute);

            // Set up the alarm again
            Intent intent = new Intent("ALARM");
            intent.putExtra("isCancelled", "No");
            pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            long time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (Calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
        }



        alarmToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            long time;
            //if Alarm is On
            if (isChecked){
                Toast.makeText(activity, "ALARM ON", Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();


                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

                activity.registerReceiver(alarmReceiver, new IntentFilter("ALARM"), Context.RECEIVER_NOT_EXPORTED);

                intent = new Intent("ALARM");
                intent.putExtra("isCancelled","No");

                // we call broadcast using pendingIntent to trigger the alarm sound
                pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                if (System.currentTimeMillis() > time) {
                    // setting time as AM and PM
                    if (Calendar.AM_PM == 0)
                        time = time + (1000 * 60 * 60 * 12);
                    else
                        time = time + (1000 * 60 * 60 * 24);
                }
                // Alarm rings continuously until toggle button is turned off
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000,pendingIntent);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(ALARM_STATE_KEY, true); // For turning on
                editor.apply();
            } else {

                if (pendingIntent != null) {

                    // Cancel the alarm immediately
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                    // Inform user
                    Toast.makeText(activity, "ALARM OFF", Toast.LENGTH_SHORT).show();

                    // Clear saved state
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove(ALARM_STATE_KEY); // Remove saved state
                    editor.apply();

                    // Send broadcast to inform that alarm is cancelled
                    Intent cancelIntent = new Intent("ALARM");
                    cancelIntent.putExtra("isCancelled", "Yes");
                    activity.sendBroadcast(cancelIntent);

                    // Reset intent and pendingIntent
                    intent = null;
                    pendingIntent = null;
                }
            }

        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        // Save the PendingIntent
        if (pendingIntent != null) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(ALARM_STATE_KEY, true); // Save alarm state as ON
            editor.putInt(ALARM_HOUR_KEY, alarmTimePicker.getCurrentHour());
            editor.putInt(ALARM_MINUTE_KEY, alarmTimePicker.getCurrentMinute());
            editor.apply();
        }
    }



}

