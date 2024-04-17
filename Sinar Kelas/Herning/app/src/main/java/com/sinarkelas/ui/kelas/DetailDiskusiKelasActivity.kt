package com.sinarkelas.ui.kelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.DetailDiskusiKelasContract
import com.sinarkelas.presenter.DetailDiskusiKelasPresenter
import com.sinarkelas.ui.kelas.fragment.adapter.AdapterKomentarDiskusi
import com.sinarkelas.ui.kelas.model.ListKomentarDiskusiModel
import com.sinarkelas.util.CustomProgressDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_diskusi_kelas.*
import java.lang.Exception
import java.text.SimpleDateFormat

class DetailDiskusiKelasActivity : AppCompatActivity(), DetailDiskusiKelasContract.detailDiskusiView {

    private var presenter : DetailDiskusiKelasContract.detailDiskusiPresenter? = null
    var idDosenKirim : String? = ""
    var idKelasKirim : String? = ""
    var statusUserKirim : String? = ""
    var idMahasiswaKirim : String? = ""
    var idInformasiKirim : String? = ""
    var lampiranFile : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_diskusi_kelas)

        presenter = DetailDiskusiKelasPresenter(this)

        var sharedPreferences : SharedPreferences = this!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        idInformasiKirim = intent.getStringExtra("idInformasi")

        idDosenKirim = idDosen
        idKelasKirim = idKelas
        statusUserKirim = statusUser
        idMahasiswaKirim = idMahasiswa

        ivSendKomentarKelas.setOnClickListener {
            kirimKomentar()
        }

        ivBackDetailDiskusiKelas.setOnClickListener {
            var intent = Intent(this, DetailKelasActivity::class.java)
            startActivity(intent).apply {
                finish()
            }
        }

        if (statusUser == "1"){
            presenter?.sendGetDetail(idKelas!!, idInformasiKirim!!, idDosen!!, "0")
        } else{
            presenter?.sendGetDetail(idKelas!!, idInformasiKirim!!, idDosen!!, idMahasiswaKirim!!)
        }

        presenter?.sendGetListKomentar(idKelas!!, idInformasiKirim!!)

    }

    override fun showToast(message: String) {

    }

    override fun showLoading() {
        svListKomentarDiskusiKelas.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListKomentarDiskusiKelas.hideShimmerAdapter()
    }

    override fun getDetail(
        idInformasi: String,
        deskripsiInformasi: String,
        fileInformasi: String,
        namaLengkap: String,
        tanggal: String,
        statusHapusInformasi: String,
        fotoProfile : String
    ) {

        var pattern = "HH:mm:ss, dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(tanggal))

        tvNamaUserDetailDiskusiKelas.text = namaLengkap
        tvTanggalDetailDiskusiKelas.text = tanggalJoinFormat
        tvIsiDiskusiDetailDiskusiKelas.text = deskripsiInformasi
        tvLampiranDetailDiskusiKelas.text = fileInformasi

        Picasso.get().load("https://sinarcenter.id/sinarkelasimage/"+fotoProfile).into(ivFotoUserDetailDiskusiKelas)

        tvLampiranDetailDiskusiKelas.setOnClickListener {
            bacaFile()
        }

        lampiranFile = fileInformasi
    }

    override fun getKomentar(data: MutableList<ListKomentarDiskusiModel>) {
        svListKomentarDiskusiKelas.apply {
            svListKomentarDiskusiKelas.layoutManager = LinearLayoutManager(this@DetailDiskusiKelasActivity, LinearLayoutManager.VERTICAL, false)
            svListKomentarDiskusiKelas.adapter = AdapterKomentarDiskusi(data)
        }
    }

    fun kirimKomentar(){
        val komentarKirim = edtKomentarKelas.text.toString()

        if (komentarKirim.isBlank()){
            Toast.makeText(this, "Kolom komentar tidak boleh kosong", Toast.LENGTH_LONG).show()
        } else{
            if (statusUserKirim == "1"){
                presenter?.sendKomentar(idKelasKirim!!, idDosenKirim!!, "0", komentarKirim, idInformasiKirim!!)
            } else{
                presenter?.sendKomentar(idKelasKirim!!, idDosenKirim!!, idMahasiswaKirim!!, komentarKirim, idInformasiKirim!!)
            }

        }
    }

    fun bacaFile(){
        try{
            var uri = Uri.parse("https://sinarcenter.id/fileuploadapps/" + lampiranFile)

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

    override fun successKirimKomentar() {
        svListKomentarDiskusiKelas.adapter!!.notifyDataSetChanged()
        presenter?.sendGetListKomentar(idKelasKirim!!, idInformasiKirim!!)
        edtKomentarKelas.setText("")
    }
}