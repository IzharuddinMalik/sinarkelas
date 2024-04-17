package com.sinarkelas.ui.kelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.MahasiswaJoinKelasContract
import com.sinarkelas.presenter.MahasiswaJoinKelasPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeMahasiswa
import com.sinarkelas.util.CustomProgressDialog
import kotlinx.android.synthetic.main.activity_mahasiswa_join_kelas.*

class MahasiswaJoinKelasActivity : AppCompatActivity(), MahasiswaJoinKelasContract.mhsJoinKelasView {

    var presenter : MahasiswaJoinKelasContract.mhsJoinKelasPresenter? = null
    var idMahasiswaKirim : String? = ""
    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mahasiswa_join_kelas)

        presenter = MahasiswaJoinKelasPresenter(this)

        var sharedPreferences : SharedPreferences = this.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        idMahasiswaKirim = idMahasiswa

        cvGabungKelas.setOnClickListener {
            joinKelas()
        }
    }

    fun joinKelas(){
        val kodeKelas = tietKodeKelas.text.toString()

        if (kodeKelas.isBlank()){
            Toast.makeText(this, "Kode Kelas Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
        } else{
            presenter?.joinKelas(kodeKelas,idMahasiswaKirim!!)
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

    override fun successJoin() {
        startActivity(Intent(this, DashboardHomeMahasiswa::class.java))
        finish()
    }
}