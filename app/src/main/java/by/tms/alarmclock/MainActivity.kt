package by.tms.alarmclock

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var hour = 0
    var minute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timePicker.setOnTimeChangedListener { view, pickerHour, pickerMinute ->
            hour = pickerHour
            minute = pickerMinute
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        setAlarm.setOnClickListener {
            val calendarNow = Calendar.getInstance()
            val calendarAlarm = Calendar.getInstance()

            calendarAlarm.set(Calendar.HOUR_OF_DAY, hour)
            calendarAlarm.set(Calendar.MINUTE, minute)
            calendarAlarm.set(Calendar.SECOND, 0)

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 998, intent, 0)

            if (calendarAlarm.after(calendarNow)){
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendarAlarm.timeInMillis, pendingIntent)
            }

            Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show()

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val name = "Alarm Channel"
        val description = "Channel for Alarm Clock"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(ALARM, name, importance)
        channel.description = description
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)

    }

}
