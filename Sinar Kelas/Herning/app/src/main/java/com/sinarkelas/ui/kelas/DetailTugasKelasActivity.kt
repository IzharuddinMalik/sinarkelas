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
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.DetailTugasKelasContract
import com.sinarkelas.presenter.DetailTugasKelasPresenter
import com.sinarkelas.ui.kelas.fragment.adapter.AdapterMahasiswaUploadTugas
import com.sinarkelas.ui.kelas.model.ListMahasiswaKumpulTugasModel
import com.sinarkelas.util.RealPath
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_detail_tugas_kelas.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat

class DetailTugasKelasActivity : AppCompatActivity(), DetailTugasKelasContract.detailTugasView {

    var presenter : DetailTugasKelasContract.detailTugasPresenter? = null
    var idDosenKirim : String? = ""
    var idKelasKirim : String? = ""
    var statusUserKirim : String? = ""
    var idMahasiswaKirim : String? = ""
    var idTugasKirim : String? = ""
    var lampiranFile : String? = ""

    var idDosenRequestBody : RequestBody? = null
    var idKelasRequestBody : RequestBody? = null
    var idMahasiswaRequestBody : RequestBody? = null
    var idTugasRequestBody : RequestBody? = null

    var selectUriFile : Uri? = null
    var fileUpload : String? = ""

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var stylingUtil : StylingUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tugas_kelas)

        var sharedPreferences : SharedPreferences = this!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        idTugasKirim = intent.getStringExtra("idTugas")
        idDosenKirim = idDosen
        idKelasKirim = idKelas
        idMahasiswaKirim = idMahasiswa

        presenter = DetailTugasKelasPresenter(this)
        stylingUtil = StylingUtil()

        presenter?.sendDetailTugas(idTugasKirim!!, idKelas!!, idDosen!!)

        if (statusUser != "1"){
            cvUploadTugasMahasiswa.visibility = View.VISIBLE
            tvUploadFileTugasMahasiswa.visibility = View.VISIBLE
            tvListMahasiswaUploadTugas.visibility = View.VISIBLE

            tvUploadFileTugasMahasiswa.setOnClickListener {
                openFile()
            }

            cvUploadTugasMahasiswa.setOnClickListener {
                kirimTugas()
            }
            presenter?.getCekStatusTugasMahasiswa(idTugasKirim!!, idKelasKirim!!, idDosenKirim!!, idMahasiswa!!)
        } else{
            cvUploadTugasMahasiswa.visibility = View.GONE
            tvUploadFileTugasMahasiswa.visibility = View.GONE
            tvListMahasiswaUploadTugas.visibility = View.GONE

            presenter?.sendListTugasDikumpul(idDosen!!, idKelas!!, idTugasKirim!!)
        }

        ivBackDetailTugasKelas.setOnClickListener{
            startActivity(Intent(this, DetailKelasActivity::class.java)).apply { finish() }
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

            tvUploadFileTugasMahasiswa.setText(fileUpload)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        svListMahasiswaUploadTugas.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListMahasiswaUploadTugas.hideShimmerAdapter()
    }

    override fun getDetailTugas(
        idTugas: String,
        judulTugas: String,
        deskripsiTugas: String,
        idDosen: String,
        namaDosen: String,
        tanggalUpload: String,
        idKelas: String,
        fileTugas: String,
        pointugas: String,
        tenggatWaktu: String,
        statusHapusTugas: String
    ) {
        var pattern = "HH:mm:ss, dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(tenggatWaktu))

        tvJudulDetailTugasKelas.text = judulTugas
        tvDeskripsiDetailTugasKelas.text = deskripsiTugas
        tvNamaDosenDetailTugasKelas.text = namaDosen
        tvTenggatWaktuDetailTugasKelas.text = tanggalJoinFormat
        tvPointDetailTugasKelas.text = pointugas + " Point"
        tvFileDetailTugasKelas.text = fileTugas

        lampiranFile = fileTugas

        tvFileDetailTugasKelas.setOnClickListener {
            bacaFile()
        }

        stylingUtil?.montserratRegularTextview(this, tvNamaDosenDetailTugasKelas)
        stylingUtil?.montserratRegularTextview(this, tvPointDetailTugasKelas)
        stylingUtil?.montserratRegularTextview(this, tvTenggatWaktuDetailTugasKelas)
        stylingUtil?.montserratRegularTextview(this, tvFileDetailTugasKelas)
        stylingUtil?.montserratRegularTextview(this, tvJudulDetailTugasKelas)
        stylingUtil?.montserratRegularTextview(this, tvDeskripsiDetailTugasKelas)
        stylingUtil?.montserratRegularTextview(this, tvListMahasiswaUploadTugas)
    }

    override fun successUploadTugas() {
        startActivity(Intent(this, DetailKelasActivity::class.java)).apply {
            finish()
        }
    }

    override fun getFileTugas(namaFile: String) {
        tvUploadFileTugasMahasiswa.text = namaFile
    }

    override fun getSelesaiTugas(selesaiTugas: String) {
        if (selesaiTugas.equals("1")){
            tvUploadFileTugasMahasiswa.isEnabled = false
            cvUploadTugasMahasiswa.visibility = View.GONE
            tvListMahasiswaUploadTugas.visibility = View.GONE
        } else{
            tvUploadFileTugasMahasiswa.isEnabled = true
            cvUploadTugasMahasiswa.visibility = View.VISIBLE
            tvListMahasiswaUploadTugas.visibility = View.VISIBLE
        }
    }

    override fun getListMahasiswaKumpulTugas(data: MutableList<ListMahasiswaKumpulTugasModel>) {
        svListMahasiswaUploadTugas.apply {
            svListMahasiswaUploadTugas.layoutManager = LinearLayoutManager(this@DetailTugasKelasActivity, LinearLayoutManager.VERTICAL, false)
            svListMahasiswaUploadTugas.adapter = AdapterMahasiswaUploadTugas(data)
        }
    }

    fun kirimTugas(){
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
            var part : MultipartBody.Part = MultipartBody.Part.createFormData("filetugas", file.name, requestFile)
            idKelasRequestBody = RequestBody.create(MediaType.parse("text/plain"), idKelasKirim)
            idDosenRequestBody = RequestBody.create(MediaType.parse("text/plain"), idDosenKirim)
            idTugasRequestBody = RequestBody.create(MediaType.parse("text/plain"), idTugasKirim)
            idMahasiswaRequestBody = RequestBody.create(MediaType.parse("text/plain"), idMahasiswaKirim)

            presenter?.sendUploadTugas(idDosenRequestBody!!, idKelasRequestBody!!, idMahasiswaRequestBody!!, idTugasRequestBody!!, part)
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
            idKelasRequestBody = RequestBody.create(MediaType.parse("text/plain"), idKelasKirim)
            idDosenRequestBody = RequestBody.create(MediaType.parse("text/plain"), idDosenKirim)
            idTugasRequestBody = RequestBody.create(MediaType.parse("text/plain"), idTugasKirim)
            idMahasiswaRequestBody = RequestBody.create(MediaType.parse("text/plain"), idMahasiswaKirim)

            presenter?.sendUploadTugas(idDosenRequestBody!!, idKelasRequestBody!!, idMahasiswaRequestBody!!, idTugasRequestBody!!, part)
        }
    }

    fun bacaFile(){
        try{
            var uri = Uri.parse("https://ruangkelas.biz.id/fileherning/" + lampiranFile)

            if (lampiranFile.toString().contains(".pdf")){
                var intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if (lampiranFile.toString().contains(".docx")){
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://rememberme.id/fileherning/" + lampiranFile))
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if (lampiranFile.toString().contains(".pptx")){
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://rememberme.id/fileherning/" + lampiranFile))
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if (lampiranFile.toString().contains(".xlsx")){
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://rememberme.id/fileherning/" + lampiranFile))
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if (lampiranFile.toString().contains(".doc")){
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://rememberme.id/fileherning/" + lampiranFile))
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if (lampiranFile.toString().contains(".ppt")){
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://rememberme.id/fileherning/" + lampiranFile))
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else if (lampiranFile.toString().contains(".xls")){
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://rememberme.id/fileherning/" + lampiranFile))
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        } catch (e : Exception){
            e.printStackTrace()
        }
    }
}