package by.tms.alarmclock

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


const val ALARM = "ALARM"

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 997, intent, 0)
        val notificationBuilder = context?.let {
            NotificationCompat.Builder(it, ALARM)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Alarm ringing")
                .setContentText("It's time to wake up, neo")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
            // канале
            //  .setDeleteIntent(pendingIntent context stop sevice)
            //  еще один броу кааст
        }
        val notificationManager = context?.let { NotificationManagerCompat.from(it) }
        notificationBuilder?.build()?.let { notificationManager?.notify(999, it) }

        val intentRingtone = Intent(context, RingtoneService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(intentRingtone)
        } else context?.startService(intentRingtone)
    }
}