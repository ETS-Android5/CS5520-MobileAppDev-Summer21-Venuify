package edu.neu.venuify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;


public class BackgroundService extends Service {
    public AlarmManager alarmMgr;
    int TEN_SECONDS = 10 * 1000;

    public void startTimer() {
        Intent newIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, newIntent, 0);
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (BuildConfig.DEBUG) {

            alarmMgr.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + TEN_SECONDS, alarmIntent);
        } else {
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                    AlarmManager.INTERVAL_HOUR * 3, alarmIntent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

