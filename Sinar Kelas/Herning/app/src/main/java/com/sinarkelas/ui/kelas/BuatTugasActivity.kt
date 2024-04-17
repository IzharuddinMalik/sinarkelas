package com.sinarkelas.ui.kelas

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.sinarkelas.R
import com.sinarkelas.contract.DosenBuatTugasContract
import com.sinarkelas.presenter.DosenBuatTugasPresenter
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.RealPath
import kotlinx.android.synthetic.main.activity_buat_tugas.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class BuatTugasActivity : AppCompatActivity(), DosenBuatTugasContract.dosenBuatTugasView, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    var presenter : DosenBuatTugasContract.dosenBuatTugasPresenter? = null
    var idDosenKirim : String? = ""
    var idKelasKirim : String? = ""

    var idDosenRequestBody : RequestBody? = null
    var idKelasRequestBody : RequestBody? = null
    var judulTugasRequestBody : RequestBody? = null
    var deskripsiTugasRequestBody : RequestBody? = null
    var pointTugasRequestBody : RequestBody? = null
    var tenggatWaktuRequestBody : RequestBody? = null
    var statusHapusRequestBody : RequestBody? = null

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
    var tenggatWaktu : String? = ""

    val calendar: Calendar = Calendar.getInstance()

    private var progressDialog = CustomProgressDialog()

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var selectUriFile : Uri? = null
    var fileUpload : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_tugas)

        presenter = DosenBuatTugasPresenter(this)

        var sharedPreferences : SharedPreferences = this!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")

        idDosenKirim = idDosen
        idKelasKirim = idKelas

        llBuatTugasKelas.setOnClickListener {
            buatTugas()
        }

        edtTenggatWaktuBuatTugas.setOnClickListener {
            days = calendar.get(Calendar.DAY_OF_MONTH)
            months = calendar.get(Calendar.MONTH)
            years = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, years, months,days)
            datePickerDialog.show()
        }

        tvUploadFileTugasKelas.setOnClickListener {
            openFile()
        }

        ivBackBuatTugas.setOnClickListener {
            startActivity(Intent(this, DetailKelasActivity::class.java)).apply {
                finish()
            }
        }

        verifyStoragePermissions(this)
    }

    fun openFile(){
        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
    }

    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission: Int =
            ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    //untuk mengambil gambar hasil capture camera tadi kita harus override onActivityResult dan membaca resultCode apakah sukses dan requestCode apakah dari Camera_Request
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file
            fileUpload = selectedFile!!.lastPathSegment.toString().removePrefix("raw:/storage/emulated/0/")

            selectUriFile = selectedFile

            Log.i("SELECTEDFILE", " === " + selectUriFile)

            tvFileTugasKelas.setText(fileUpload)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun successBuat() {
        startActivity(Intent(this, DetailKelasActivity::class.java)).apply {
            finish()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month+1
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hour, minute,
            DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute

        if (myHour < 10 && myMinute < 10){
            edtTenggatWaktuBuatTugas.setText("" + myYear + "-" + myMonth + "-" + myDay + " " + "0" + myHour + ":" + "0" + myMinute + ":" + "00")
        } else{
            edtTenggatWaktuBuatTugas.setText("" + myYear + "-" + myMonth + "-" + myDay + " " + myHour + ":" + myMinute + ":" + "00")
        }

    }

    fun buatTugas(){
        val judulTugas = edtJudulBuatTugas.text.toString()
        val deskripsiTugas = edtDeskripsiBuatTugas.text.toString()
        val poinTugas = edtPointBuatTugas.text.toString()

        if (myHour < 10 && myMinute < 10){
            tenggatWaktu = "" + myYear + "-" + myMonth + "-" + myDay + " " + "0" + myHour + ":" + "0" + myMinute + ":" + "00"
        } else{
            tenggatWaktu = "" + myYear + "-" + myMonth + "-" + myDay + " " + myHour + ":" + myMinute + ":" + "00"
        }


        if (judulTugas.isBlank()){
            edtJudulBuatTugas.setError("Tidak Boleh Kosong")
        } else if (deskripsiTugas.isBlank()){
            edtDeskripsiBuatTugas.setError("Tidak Boleh Kosong")
        } else if (poinTugas.isBlank()){
            edtPointBuatTugas.setError("Tidak Boleh Kosong")
        } else{

            var realPathUtils = RealPath()

            if (selectUriFile != null){
                val file = File(realPathUtils.getPath(this, selectUriFile!!))
                if (file.exists()){
                    Log.i("EXISTFILE", " === " + file)
                } else{
                    Log.i("NOEXIST", " === " + file)
                }

                val requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
                var part : MultipartBody.Part = MultipartBody.Part.createFormData("filetugas", file.name, requestFile)
                idDosenRequestBody = RequestBody.create(MediaType.parse("text/plain"), idDosenKirim)
                idKelasRequestBody = RequestBody.create(MediaType.parse("text/plain"), idKelasKirim)
                judulTugasRequestBody = RequestBody.create(MediaType.parse("text/plain"), judulTugas)
                deskripsiTugasRequestBody = RequestBody.create(MediaType.parse("text/plain"), deskripsiTugas)
                pointTugasRequestBody = RequestBody.create(MediaType.parse("text/plain"), poinTugas)
                tenggatWaktuRequestBody = RequestBody.create(MediaType.parse("text/plain"), tenggatWaktu)
                statusHapusRequestBody = RequestBody.create(MediaType.parse("text/plain"), "0")

                Log.i("TENGGATWAKTU", " === " + tenggatWaktu)

                presenter?.kirimTugas(judulTugasRequestBody!!, deskripsiTugasRequestBody!!, idDosenRequestBody!!, idKelasRequestBody!!, part, pointTugasRequestBody!!, tenggatWaktuRequestBody!!, statusHapusRequestBody!!)
            } else{
                val file = File("")
                if (file.exists()){
                    Log.i("EXISTFILE", " === " + file)
                } else{
                    Log.i("NOEXIST", " === " + file)
                }

                val requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "")
                var part : MultipartBody.Part = MultipartBody.Part.createFormData("filetugas", "", requestFile)
                idDosenRequestBody = RequestBody.create(MediaType.parse("text/plain"), idDosenKirim)
                idKelasRequestBody = RequestBody.create(MediaType.parse("text/plain"), idKelasKirim)
                judulTugasRequestBody = RequestBody.create(MediaType.parse("text/plain"), judulTugas)
                deskripsiTugasRequestBody = RequestBody.create(MediaType.parse("text/plain"), deskripsiTugas)
                pointTugasRequestBody = RequestBody.create(MediaType.parse("text/plain"), poinTugas)
                tenggatWaktuRequestBody = RequestBody.create(MediaType.parse("text/plain"), tenggatWaktu)
                statusHapusRequestBody = RequestBody.create(MediaType.parse("text/plain"), "0")

                Log.i("TENGGATWAKTU", " === " + tenggatWaktu)

                presenter?.kirimTugas(judulTugasRequestBody!!, deskripsiTugasRequestBody!!, idDosenRequestBody!!, idKelasRequestBody!!, part, pointTugasRequestBody!!, tenggatWaktuRequestBody!!, statusHapusRequestBody!!)
            }
        }
    }
}