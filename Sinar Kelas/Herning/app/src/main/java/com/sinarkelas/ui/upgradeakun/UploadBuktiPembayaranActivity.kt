package com.sinarkelas.ui.upgradeakun

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Base64
import android.view.Window
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TimePicker
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.sinarkelas.R
import com.sinarkelas.contract.UploadBuktiPembayaranContract
import com.sinarkelas.presenter.UploadBuktiPembayaranPresenter
import com.sinarkelas.util.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_upload_bukti_pembayaran.*
import java.io.ByteArrayOutputStream
import java.util.*

class UploadBuktiPembayaranActivity : AppCompatActivity(), UploadBuktiPembayaranContract.uploadBuktiPembayaranView, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var presenterUpload : UploadBuktiPembayaranContract.uploadBuktiPembayaranPresenter? = null
    var idDosen : String? = ""
    var idBilling : String? = ""

    var CAMERA_REQUEST : Int? = 0
    var gambarKu : Bitmap? = null

    //image pick code
    private val IMAGE_PICK_CODE = 1000;
    //Permission code
    private val PERMISSION_CODE = 1001;
    var resized : Bitmap? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_bukti_pembayaran)

        presenterUpload =
            UploadBuktiPembayaranPresenter(this)

        idBilling = intent.getStringExtra("idBilling")

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")

        cvBukaFileUploadBuktiPembayaran.setOnClickListener {
            dialogFoto()
        }

        llKonfirmasiBuktiPembayaran.setOnClickListener {
            konfirmasiPembayaran()
        }

        tietTanggalTransfer.setOnClickListener {
            days = calendar.get(Calendar.DAY_OF_MONTH)
            months = calendar.get(Calendar.MONTH)
            years = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, years, months,days)
            datePickerDialog.show()
        }

    }

    fun dialogFoto(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_pilihfotodosen)

        var window : Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val openKamera = dialog.findViewById<CardView>(R.id.cvOpenKameraDosen)
        val openGallery = dialog.findViewById<CardView>(R.id.cvOpenGaleriDosen)

        openKamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST!!)
        }

        openGallery.setOnClickListener {
            openImageGallery()
        }

        dialog.show()
    }

    fun openImageGallery(){
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    openImageGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //untuk mengambil gambar hasil capture camera tadi kita harus override onActivityResult dan membaca resultCode apakah sukses dan requestCode apakah dari Camera_Request
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val mphoto = (data!!.extras!!["data"] as Bitmap?)!!
            //panggil method uploadImage
            resized = Bitmap.createScaledBitmap(mphoto, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivFotoUploadBuktiPembayaran.setImageBitmap(resized)
            gambarKu = resized
        }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val photo : Bitmap = MediaStore.Images.Media.getBitmap(this!!.contentResolver, data?.data)
            resized = Bitmap.createScaledBitmap(photo, 480, 640, true)
            var outputStream = ByteArrayOutputStream()
            resized?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            ivFotoUploadBuktiPembayaran.setImageBitmap(resized)
            gambarKu = resized
        }
    }

    fun convertImageToStringForServer(imageBitmap: Bitmap?): String? {
        val stream = ByteArrayOutputStream()
        return if (imageBitmap != null) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            Base64.encodeToString(byteArray, Base64.DEFAULT)
        } else {
            null
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun showLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun successUpload(namaPengirim : String, createAt : String) {
        var intent = Intent(this, VerifikasiKonfirmasiPembayaranActivity::class.java)
        intent.putExtra("namaPengirim", namaPengirim)
        intent.putExtra("createAt", createAt)
        intent.putExtra("idVerifikasi", "2")
        startActivity(intent)
        finish()
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
            tietTanggalTransfer.setText("" + myYear + "-" + myMonth + "-" + myDay + " " + "0" + myHour + ":" + "0" + myMinute + ":" + "00")
        } else{
            tietTanggalTransfer.setText("" + myYear + "-" + myMonth + "-" + myDay + " " + myHour + ":" + myMinute + ":" + "00")
        }
    }

    fun konfirmasiPembayaran(){
        val namaPengirim = tietNamaPengirim.text.toString()
        val bankPengirim = tietBankPengirim.text.toString()
        val tanggalTransfer = tietTanggalTransfer.text.toString()

        if (namaPengirim.isBlank()){
            tietNamaPengirim.setError("Nama Pengirim Tidak Boleh Kosong!")
        } else if (bankPengirim.isBlank()){
            tietBankPengirim.setError("Nama Bank Tidak Boleh Kosong!")
        } else if (tanggalTransfer.isBlank()){
            tietTanggalTransfer.setError("Tanggal Transfer Tidak Boleh Kosong!")
        } else{

            var image = convertImageToStringForServer(gambarKu)

            if (myHour < 10 && myMinute < 10){
                tenggatWaktu = "" + myYear + "-" + myMonth + "-" + myDay + " " + "0" + myHour + ":" + "0" + myMinute + ":" + "00"
            } else{
                tenggatWaktu = "" + myYear + "-" + myMonth + "-" + myDay + " " + myHour + ":" + myMinute + ":" + "00"
            }

            if (image != null){
                presenterUpload?.sendBuktiPembayaran(idBilling!!, idDosen!!, namaPengirim, bankPengirim, tenggatWaktu!!, image, "0")
            } else{
                Toast.makeText(this, "Harap Upload File Bukti Pembayaran", Toast.LENGTH_LONG).show()
            }
        }
    }
}