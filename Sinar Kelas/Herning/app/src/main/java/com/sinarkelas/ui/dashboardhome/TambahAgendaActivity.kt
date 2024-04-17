package com.sinarkelas.ui.dashboardhome

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.format.DateFormat
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.BuatAgendaContract
import com.sinarkelas.presenter.BuatAgendaPresenter
import com.sinarkelas.ui.notifikasi.NotifikasiActivity
import com.sinarkelas.util.AlarmBroadcastReceiver
import com.sinarkelas.util.AlarmService
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.Receiver
import kotlinx.android.synthetic.main.activity_tambah_agenda.*
import java.util.*
import java.util.concurrent.TimeUnit

class TambahAgendaActivity : AppCompatActivity(), BuatAgendaContract.buatAgendaView, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var presenterBuatAgenda : BuatAgendaContract.buatAgendaPresenter? = null
    var idDosen : String? = ""

    var days = 0
    var months: Int = 0
    var years: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    var bulanWaktu : String? = ""
    var jamWaktu : String? = ""

    val calendar: Calendar = Calendar.getInstance()
    private var alarmService : AlarmService? = null

    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_agenda)

        presenterBuatAgenda = BuatAgendaPresenter(this)

        alarmService = AlarmService()

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")

        edtTanggalBuatAgenda.setOnClickListener {
            days = calendar.get(Calendar.DAY_OF_MONTH)
            months = calendar.get(Calendar.MONTH)
            years = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, years, months,days)
            datePickerDialog.show()
        }

        edtJamBuatAgenda.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            hour = calendar.get(Calendar.HOUR)
            minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(this, this, hour, minute,
                DateFormat.is24HourFormat(this))
            timePickerDialog.show()
        }

        llBuatAgenda.setOnClickListener {
            buatAgenda()
        }

        ivBackTambahAgenda.setOnClickListener {
            startActivity(Intent(this, DashboardHomeDosen::class.java))
            finish()
        }
    }

    override fun showLoadingAgenda() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoadingAgenda() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastAgenda(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun successAgenda() {
        startActivity(Intent(this, DashboardHomeDosen::class.java)).apply {
            finish()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month+1

        if (myMonth < 10){
            edtTanggalBuatAgenda.setText("" + myYear + "-" + "0" + myMonth + "-" + myDay)
        } else{
            edtTanggalBuatAgenda.setText("" + myYear + "-" + myMonth + "-" + myDay)
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute

        if (myHour < 10 && myMinute < 10){
            edtJamBuatAgenda.setText("" + "0" + myHour + ":" + "0" + myMinute + ":" + "00")
        } else{
            edtJamBuatAgenda.setText("" + myHour + ":" + myMinute + ":" + "00")
        }
    }

    fun buatAgenda(){

        var judulAgenda = edtJudulBuatAgenda.text.toString()
        var deskripsiAgenda = edtDeskripsiBuatAgenda.text.toString()

        if (myHour < 10 || myMinute < 10){
            jamWaktu = "" + "0" + myHour + ":" + "0" + myMinute + ":" + "00"
        } else{
            jamWaktu = "" + myHour + ":" + myMinute + ":" + "00"
        }

        if (myMonth<10){
            bulanWaktu = "" + myYear + "-" + "0" + myMonth + "-" + myDay

            calendar.apply {
                set(Calendar.YEAR, myYear)
                set(Calendar.MONTH,0+myMonth)
                set(Calendar.DAY_OF_MONTH, myDay)
                set(Calendar.HOUR, myHour)
                set(Calendar.MINUTE, myMinute)
                set(Calendar.SECOND, 0)
            }

        }else{
            bulanWaktu = "" + myYear + "-" + myMonth + "-" + myDay

            calendar.apply {
                set(Calendar.YEAR, myYear)
                set(Calendar.MONTH, myMonth)
                set(Calendar.DAY_OF_MONTH, myDay)
                set(Calendar.HOUR, myHour)
                set(Calendar.MINUTE, myMinute)
                set(Calendar.SECOND, 0)
            }
        }

        alarmService?.setScheduleNotification(calendar, this, jamWaktu!!)

        Log.i("CALENDAR", " === " + calendar.timeInMillis)

        bulanWaktu = "" + myYear + "-" + myMonth + "-" + myDay

        presenterBuatAgenda?.buatAgenda("" + judulAgenda, "" + deskripsiAgenda, "" + bulanWaktu, "" + jamWaktu, "" + idDosen)
    }
}