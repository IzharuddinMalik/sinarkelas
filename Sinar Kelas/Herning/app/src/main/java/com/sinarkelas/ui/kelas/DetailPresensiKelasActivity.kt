package com.sinarkelas.ui.kelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.DetailPresensiKelasContract
import com.sinarkelas.presenter.DetailPresensiKelasPresenter
import com.sinarkelas.ui.kelas.fragment.adapter.AdapterDetailPresensiKelas
import com.sinarkelas.ui.kelas.model.ListDetailPresensiKelasModel
import kotlinx.android.synthetic.main.activity_detail_presensi_kelas.*

class DetailPresensiKelasActivity : AppCompatActivity(), DetailPresensiKelasContract.detailPresensiKelasView {

    private var presenter : DetailPresensiKelasContract.detailPresensiKelasPresenter? = null
    var idPresensi : String? = ""
    var tanggalPresensi : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_presensi_kelas)

        presenter = DetailPresensiKelasPresenter(this)

        var sharedPreferences : SharedPreferences = this!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")
        idPresensi = intent.getStringExtra("idPresensiKelas")
        tanggalPresensi = intent.getStringExtra("tanggalPresensi")

        presenter?.sendDetailPresensi(idKelas!!, idDosen!!, idPresensi!!)

        if(statusUser == "1"){
            llTotalHadir.visibility = View.VISIBLE
            presenter?.getJumlahHadirPresensi(idKelas!!, idDosen!!, idPresensi!!)
            llMahasiswaPresensiKelas.visibility = View.GONE
        }else{
            llTotalHadir.visibility = View.GONE
            llMahasiswaPresensiKelas.visibility = View.VISIBLE
        }

        ivBackDaftarPresensiMahasiswa.setOnClickListener {
            startActivity(Intent(this, DetailKelasActivity::class.java))
            finish()
        }

        llMahasiswaPresensiKelas.setOnClickListener {
            var intent = Intent(this, MahasiswaPresensiKelasActivity::class.java)
            intent.putExtra("tanggalPresensi", tanggalPresensi)
            intent.putExtra("idPresensiKelas", idPresensi)
            startActivity(intent)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        svListPresensiDetailKelas.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListPresensiDetailKelas.hideShimmerAdapter()
    }

    override fun getListDetailPresensi(data: MutableList<ListDetailPresensiKelasModel>) {
        svListPresensiDetailKelas.apply {
            svListPresensiDetailKelas.layoutManager = LinearLayoutManager(this@DetailPresensiKelasActivity, LinearLayoutManager.VERTICAL, false)
            svListPresensiDetailKelas.adapter = AdapterDetailPresensiKelas(data)
        }
    }

    override fun getJumlahHadir(jumlahHadir: String, jumlahIzin: String, jumlahAbsen: String) {
        tvJumlahMahasiswaHadirPresensi.text = jumlahHadir
        tvJumlahMahasiswaIzinPresensi.text = jumlahIzin
        tvJumlahMahasiswaAbsenPresensi.text = jumlahAbsen
    }

    override fun onBackPressed() {
        startActivity(Intent(this, DetailKelasActivity::class.java)).apply { finish() }
    }
}