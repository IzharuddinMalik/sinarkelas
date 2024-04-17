package com.sinarkelas.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import java.text.SimpleDateFormat
import java.util.*

class AlarmBroadcastReceiver : BroadcastReceiver() {

    private var notificationUtil : NotificationUtil? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        notificationUtil = NotificationUtil()
        if (intent != null) {
            // pengecekan dilakukan agar notifikasi tidak muncul berulang
            if (getTimeNow() == intent.getStringExtra("validationTime")) {
                if (context != null) notificationUtil?.createNotificationChannel(context)
            }

            if (intent.action == "android.intent.action.TIME_SET") {
                context?.stopService(Intent(context, AlarmService::class.java))

                // langkah ini dilakukan untuk memicu ulang agar service kembali menyala
                // setelah melakukan uji coba mengganti tanggal service mati
                Handler().postDelayed({ context?.startService(Intent(context, AlarmService::class.java)) }, 1000)
            }
        }
    }

    private fun getTimeNow(): String {
        val dateTimeMillis = System.currentTimeMillis()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateTimeMillis

        return SimpleDateFormat("HH:mm:ss").format(calendar.time)
    }

}