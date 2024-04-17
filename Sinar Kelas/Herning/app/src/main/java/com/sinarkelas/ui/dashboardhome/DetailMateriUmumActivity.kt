package com.sinarkelas.ui.dashboardhome

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.DetailMateriUmumContract
import com.sinarkelas.presenter.DetailMateriUmumPresenter
import kotlinx.android.synthetic.main.activity_detail_materi_umum.*
import java.lang.Exception

class DetailMateriUmumActivity : AppCompatActivity(), DetailMateriUmumContract.materiUmumView {

    var idMateriUmumKirim : String? = ""
    var presenter : DetailMateriUmumContract.materiUmumPresenter? = null
    var lampiranFile : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_materi_umum)

        idMateriUmumKirim = intent.getStringExtra("idmateriumum")
        var sharedPreferences : SharedPreferences = this.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")

        presenter = DetailMateriUmumPresenter(this)

        if (statusUser == "1"){
            presenter?.getDetailMateriUmum(idDosen!!, idMateriUmumKirim!!)
        } else{
            presenter?.getDetailMateriUmum(idMahasiswa!!, idMateriUmumKirim!!)
        }

        ivBackMateriUmum.setOnClickListener {
            if (statusUser == "1"){
                startActivity(Intent(this, DashboardHomeDosen::class.java))
            } else{
                startActivity(Intent(this, DashboardHomeMahasiswa::class.java))
            }

        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun detailMateri(
        namaJudul: String,
        fileMateri: String,
        sumberMateri : String,
        tanggalMateri: String,
        namaAdmin: String
    ) {
        tvJudulMateriUmum.text = namaJudul
        tvFileMateriUmum.text = fileMateri
        tvTanggalMateri.text = tanggalMateri
        tvNamaAdmin.text = namaAdmin
        tvSumberMateriUmum.text = sumberMateri

        lampiranFile = fileMateri

        tvFileMateriUmum.setOnClickListener {
            bacaFile()
        }
    }

    fun bacaFile(){
        try{
            var uri = Uri.parse("http://rememberme.id/fileherning/" + lampiranFile)

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