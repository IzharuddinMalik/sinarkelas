package com.sinarkelas.ui.kelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.BuatPresensiKelasContract
import com.sinarkelas.presenter.BuatPresensiKelasPresenter
import com.sinarkelas.util.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_buat_presensi_kelas.*

class BuatPresensiKelasActivity : AppCompatActivity(), BuatPresensiKelasContract.buatPresensiView {

    private var presenter : BuatPresensiKelasContract.buatPresensiPresenter? = null
    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buat_presensi_kelas)

        presenter = BuatPresensiKelasPresenter(this)

        var sharedPreferences : SharedPreferences = this!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")

        llBuatPresensiKelas.setOnClickListener {
            presenter?.buatPresensi(idKelas!!, idDosen!!)
        }

        ivBackBuatPresensi.setOnClickListener {
            startActivity(Intent(this, DetailKelasActivity::class.java)).apply {
                finish()
            }
        }

    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun success() {
        startActivity(Intent(this, DetailKelasActivity::class.java))
        finish()
    }
}