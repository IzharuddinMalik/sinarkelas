package com.sinarkelas.util

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import java.util.*

class AlarmService : Service() {
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun stopTimer() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun setScheduleNotification(calendar: Calendar, context: Context, jamWaktu : String) {

        // membuat objek intent yang mana akan menjadi target selanjutnya
        // bisa untuk berpindah halaman dengan dan tanpa data.
        val intent = Intent(context, AlarmBroadcastReceiver::class.java)
        intent.putExtra("validationTime", jamWaktu)

        Log.i("CALENDAR", " === " + calendar.timeInMillis)
        Log.i("JAMWAKTU", " === " + jamWaktu)

        // membuat objek PendingIntent yang berguna sebagai penampung intent dan aksi yang akan dikerjakan
        val requestCode = 0
        val pendingIntent =
            PendingIntent.getBroadcast(context, requestCode, intent, 0)

        // membuat objek AlarmManager untuk melakukan pendataran alarm yang akan dijadwalkan
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // kita buat alarm yang dapat berfungsi walaupun dalam kondisi hp idle dan tepat waktu
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopTimer()
    }
}