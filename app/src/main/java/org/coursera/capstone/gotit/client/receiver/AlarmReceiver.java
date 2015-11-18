package org.coursera.capstone.gotit.client.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import org.coursera.capstone.gotit.client.CallableTask;
import org.coursera.capstone.gotit.client.TaskCallback;
import org.coursera.capstone.gotit.client.model.GeneralSettings;
import org.coursera.capstone.gotit.client.notification.NotificationFactory;
import org.coursera.capstone.gotit.client.provider.ServiceApi;
import org.coursera.capstone.gotit.client.provider.ServiceCall;

import java.util.Calendar;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getName();

    private static final Class[] ALARM_LIST = {Alarm1.class, Alarm2.class, Alarm3.class};

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Alarm "+getClass());

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

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
                return srv.loadAlertsSettings(userId, alertKey[0], alertKey[1], alertKey[2]);
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