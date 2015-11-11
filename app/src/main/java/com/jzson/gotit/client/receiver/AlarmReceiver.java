package com.jzson.gotit.client.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.jzson.gotit.client.CallableTask;
import com.jzson.gotit.client.TaskCallback;
import com.jzson.gotit.client.model.GeneralSettings;
import com.jzson.gotit.client.model.Notification;
import com.jzson.gotit.client.notification.NotificationFactory;
import com.jzson.gotit.client.provider.ServiceApi;
import com.jzson.gotit.client.provider.ServiceCall;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getName();

    private static final Class[] ALARM_LIST = {Alarm1.class, Alarm2.class, Alarm3.class};

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Alarm "+getClass());

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        Toast.makeText(context, "Alarm! "+getClass(), Toast.LENGTH_LONG).show();
        Log.i(TAG, "Prepare for creating notification");
        NotificationFactory.createCheckInNotification(context);
        Log.i(TAG, "Notification created");

        wl.release();
    }

    public static void setAlarm(Context context, Class alarmClazz, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, alarmClazz);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pi); // Millisec * Second * Minute
    }

    public static void cancelAlarm(Context context, Class alarmClazz) {
        Intent intent = new Intent(context, alarmClazz);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public static void updateAlarms(final Context context, final int userId, final String[] alertKey) {
        CallableTask.invoke(context, new ServiceCall<List<GeneralSettings>>() {
            @Override
            public List<GeneralSettings> call(ServiceApi srv) throws Exception {
                return srv.loadGeneralSettingsList(userId, alertKey);
            }
        }, new TaskCallback<List<GeneralSettings>>() {
            @Override
            public void success(List<GeneralSettings> settingsList) {
                int alarmIndex = 0;
                for (GeneralSettings settings : settingsList) {
                    String time = settings.getValue();
                    Class alarmClazz = ALARM_LIST[alarmIndex++];
                    if (time != null) {
                        String[] times = time.split(":");

                        int hour = Integer.parseInt(times[0]);
                        int min = Integer.parseInt(times[1]);

                        setAlarm(context, alarmClazz, hour, min);
                    } else {
                        cancelAlarm(context, alarmClazz);
                    }
                }
            }

            @Override
            public void error(Exception e) {

            }
        });
    }
}