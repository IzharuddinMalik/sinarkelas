package com.sinarkelas.ui.kelas

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.sinarkelas.R
import com.sinarkelas.contract.BuatInformasiKelasContract
import com.sinarkelas.presenter.BuatInformasiKelasPresenter
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.RealPath
import kotlinx.android.synthetic.main.activity_buat_diskusi_kelas.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class BuatDiskusiKelasActivity : AppCompatActivity(), BuatInformasiKelasContract.buatInformasiView {

    private var presenter : BuatInformasiKelasContract.buatInformasiPresenter? = null

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var idDosenKirim : String? = ""
    var idKelasKirim : String? = ""

    var deskripsiRequestBody : RequestBody? = null
    var idKelasRequestBody : RequestBody? = null
    var idDosenRequestBody : RequestBody? = null
    var statusHapusRequestBody : RequestBody? = null
    var idMahasiswaRequestBody : RequestBody? = null

    var statusUserKirim : String? = ""
    var idMahasiswaKirim : String? = ""

    var selectUriFile : Uri? = null
    var fileUpload : String? = ""

    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_diskusi_kelas)

        presenter = BuatInformasiKelasPresenter(this)

        var sharedPreferences : SharedPreferences = this!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        statusUserKirim = statusUser
        idMahasiswaKirim = idMahasiswa

        idDosenKirim = idDosen
        idKelasKirim = idKelas

        llBagikanDiskusiKelas.setOnClickListener {
            bagikanDiskusi()
        }

        tvUploadFileInformasiDiskusiKelas.setOnClickListener {
            openFile()
        }

        ivBackBagikanDiskusi.setOnClickListener {
            startActivity(Intent(this, DetailKelasActivity::class.java))
            finish()
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

            tvFileInformasiDiskusiKelas.setText(fileUpload)
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

    fun bagikanDiskusi(){
        val deskripsiInformasi = tietBagikanDiskusiKelas.text.toString()

        if (deskripsiInformasi.isBlank()){
            Toast.makeText(this, "Harap Isi Deskripsi", Toast.LENGTH_LONG).show()
        } else{

            var realPathUtils = RealPath()

            if(selectUriFile!=null){
                val file = File(realPathUtils.getPath(this, selectUriFile!!))
                if (file.exists()){
                    Log.i("EXISTFILE", " === " + file)
                } else{
                    Log.i("NOEXIST", " === " + file)
                }

                val requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
                var part : MultipartBody.Part = MultipartBody.Part.createFormData("fileinformasi", file.name, requestFile)
                deskripsiRequestBody = RequestBody.create(MediaType.parse("text/plain"), deskripsiInformasi)
                idKelasRequestBody = RequestBody.create(MediaType.parse("text/plain"), idKelasKirim)
                idDosenRequestBody = RequestBody.create(MediaType.parse("text/plain"), idDosenKirim)
                statusHapusRequestBody = RequestBody.create(MediaType.parse("text/plain"), "0")

                if (statusUserKirim == "1"){
                    idMahasiswaRequestBody = RequestBody.create(MediaType.parse("text/plain"), "0")
                } else{
                    idMahasiswaRequestBody = RequestBody.create(MediaType.parse("text/plain"), idMahasiswaKirim)
                }

                presenter?.sendBuatInformasi(deskripsiRequestBody!!, idKelasRequestBody!!, idDosenRequestBody!!, idMahasiswaRequestBody!!, part, statusHapusRequestBody!!)
            } else{
                val file = File("")
                if (file.exists()){
                    Log.i("EXISTFILE", " === " + file)
                } else{
                    Log.i("NOEXIST", " === " + file)
                }

                val requestFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), "")
                var part : MultipartBody.Part = MultipartBody.Part.createFormData("fileinformasi", "", requestFile)
                deskripsiRequestBody = RequestBody.create(MediaType.parse("text/plain"), deskripsiInformasi)
                idKelasRequestBody = RequestBody.create(MediaType.parse("text/plain"), idKelasKirim)
                idDosenRequestBody = RequestBody.create(MediaType.parse("text/plain"), idDosenKirim)
                statusHapusRequestBody = RequestBody.create(MediaType.parse("text/plain"), "0")

                if (statusUserKirim == "1"){
                    idMahasiswaRequestBody = RequestBody.create(MediaType.parse("text/plain"), "0")
                } else{
                    idMahasiswaRequestBody = RequestBody.create(MediaType.parse("text/plain"), idMahasiswaKirim)
                }

                presenter?.sendBuatInformasi(deskripsiRequestBody!!, idKelasRequestBody!!, idDosenRequestBody!!, idMahasiswaRequestBody!!, part, statusHapusRequestBody!!)
            }
        }
    }

    override fun successBuat() {
        startActivity(Intent(this, DetailKelasActivity::class.java)).apply {
            finish()
        }
    }
}