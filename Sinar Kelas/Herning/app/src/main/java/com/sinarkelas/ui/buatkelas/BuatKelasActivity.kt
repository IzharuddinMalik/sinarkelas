package com.sinarkelas.ui.buatkelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.BuatKelasDosenContract
import com.sinarkelas.presenter.BuatKelasDosenPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.util.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_buat_kelas.*

class BuatKelasActivity : AppCompatActivity(), BuatKelasDosenContract.buatKelasView {

    private var presenter : BuatKelasDosenContract.buatKelasPresenter? = null
    var idDosenKirim : String? = ""
    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_kelas)

        var sharedPreferences : SharedPreferences = this!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")

        idDosenKirim = idDosen

        presenter = BuatKelasDosenPresenter(this)

        llBuatKelas.setOnClickListener {
            buatKelas()
        }

        tvBackBuatKelas.setOnClickListener {
            startActivity(Intent(this, DashboardHomeDosen::class.java))
            finish()
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

    fun buatKelas(){

        val namaKelas = tietNamaKelasBuatKelas.text.toString()
        val namaMataKuliah = tietNamaMataKuliahBuatKelas.text.toString()
        val deskripsiKelas = tietDeskripsiKelasBuatKelas.text.toString()

        if (namaKelas.isBlank()){
            Toast.makeText(this, "Nama Kelas Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
        } else if (namaMataKuliah.isBlank()){
            Toast.makeText(this, "Nama Mata Kuliah Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
        } else if (deskripsiKelas.isBlank()){
            Toast.makeText(this, "Deskripsi Kelas Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
        } else{
            presenter?.buatKelas(namaMataKuliah, namaKelas, deskripsiKelas, idDosenKirim!!, "-")
        }

    }

    override fun success() {
        startActivity(Intent(this, DashboardHomeDosen::class.java))
        finish()
    }
}