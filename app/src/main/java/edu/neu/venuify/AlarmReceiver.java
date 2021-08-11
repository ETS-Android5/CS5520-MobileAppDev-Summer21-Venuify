package edu.neu.venuify;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, HomePage.class);
        newIntent.putExtra("stopTimer", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyVenuify")
                .setSmallIcon(R.drawable.ic_baseline_priority_high_24)
                .setContentTitle("Book a Venu with Venuify today!")
                .setContentText("Hundreds of venues have been booked this week! You're missing out!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(42, builder.build());
    }
}
