package com.sinarkelas.ui.dashboardhome

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.BuatAgendaMahasiswaContract
import com.sinarkelas.presenter.BuatAgendaMahasiswaPresenter
import com.sinarkelas.util.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_tambah_agenda.*
import kotlinx.android.synthetic.main.activity_tambah_agenda_mahasiswa.*
import java.util.*

class TambahAgendaMahasiswaActivity : AppCompatActivity(), BuatAgendaMahasiswaContract.buatAgendaMahasiswaView, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var presenterTambahAgenda : BuatAgendaMahasiswaContract.buatAgendaMahasiswaPresenter? = null
    private var idMahasiswa : String? = ""
    private var progressDialog = CustomProgressDialog()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_agenda_mahasiswa)

        presenterTambahAgenda = BuatAgendaMahasiswaPresenter(this)
        var sharedPreferences : SharedPreferences = this.getSharedPreferences("session", Context.MODE_PRIVATE)
        idMahasiswa = sharedPreferences.getString("idMahasiswa", "")

        edtTanggalBuatAgendaMahasiswa.setOnClickListener {
            days = calendar.get(Calendar.DAY_OF_MONTH)
            months = calendar.get(Calendar.MONTH)
            years = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, years, months,days)
            datePickerDialog.show()
        }

        edtJamBuatAgendaMahasiswa.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            hour = calendar.get(Calendar.HOUR)
            minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(this, this, hour, minute,
                DateFormat.is24HourFormat(this))
            timePickerDialog.show()
        }

        llBuatAgendaMahasiswa.setOnClickListener {
            tambahAgenda()
        }

        ivBackTambahAgendaMahasiswa.setOnClickListener {
            startActivity(Intent(this, DashboardHomeMahasiswa::class.java))
            finish()
        }
    }

    override fun showToastAgenda(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun showLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun successBuat() {
        startActivity(Intent(this, DashboardHomeMahasiswa::class.java))
        finish()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month+1

        if (myMonth < 10){
            edtTanggalBuatAgendaMahasiswa.setText("" + myYear + "-" + "0" + myMonth + "-" + myDay)
        } else{
            edtTanggalBuatAgendaMahasiswa.setText("" + myYear + "-" + myMonth + "-" + myDay)
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute

        if (myHour < 10 && myMinute < 10){
            edtJamBuatAgendaMahasiswa.setText("" + "0" + myHour + ":" + "0" + myMinute + ":" + "00")
        } else{
            edtJamBuatAgendaMahasiswa.setText("" + myHour + ":" + myMinute + ":" + "00")
        }
    }

    fun tambahAgenda(){
        var judulAgenda = edtJudulBuatAgendaMahasiswa.text.toString()
        var deskripsiAgenda = edtDeskripsiBuatAgendaMahasiswa.text.toString()

        if (myHour < 10 && myMinute < 10){
            jamWaktu = "" + "0" + myHour + ":" + "0" + myMinute + ":" + "00"
        } else{
            jamWaktu = "" + myHour + ":" + myMinute + ":" + "00"
        }

        if (myMonth<10){
            bulanWaktu = "" + myYear + "-" + "0" + myMonth + "-" + myDay
        }else{
            bulanWaktu = "" + myYear + "-" + myMonth + "-" + myDay
        }

        bulanWaktu = "" + myYear + "-" + myMonth + "-" + myDay

        presenterTambahAgenda?.sendAgenda("" + judulAgenda, "" + deskripsiAgenda, "" + bulanWaktu, "" + jamWaktu, "" + idMahasiswa)
    }
}