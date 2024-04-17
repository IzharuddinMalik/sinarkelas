package com.sinarkelas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinarkelas.contract.KritikDanSaranContract
import com.sinarkelas.presenter.KritikDanSaranPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.ui.dashboardhome.DashboardHomeMahasiswa
import kotlinx.android.synthetic.main.activity_kritikdansaran.*

class KritikdansaranActivity : AppCompatActivity(), KritikDanSaranContract.kritiksaranView {

    var presenter : KritikDanSaranContract.kritiksaranPresenter? = null
    var namaPengirim : String? = ""
    var emailPengirim : String? = ""
    var statuUserKirim : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kritikdansaran)

        presenter = KritikDanSaranPresenter(this)

        var sharedPreferences : SharedPreferences = this.getSharedPreferences("session", Context.MODE_PRIVATE)
        var statusUser : String? = sharedPreferences.getString("statusUser", "")

        namaPengirim = intent.getStringExtra("namapengirim")
        emailPengirim = intent.getStringExtra("emailpengirim")

        if (statusUser=="1"){
            ivBackKritikDanSaran.setOnClickListener {
                startActivity(Intent(this, DashboardHomeDosen::class.java))
            }
        } else{
            ivBackKritikDanSaran.setOnClickListener {
                startActivity(Intent(this, DashboardHomeMahasiswa::class.java))
            }
        }

        llBagikanKritikDanSaran.setOnClickListener {
            kirimKritikDanSaran()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    fun kirimKritikDanSaran(){
        val kritik = tietBagikanKritik.text.toString()
        val saran = tietBagikanSaran.text.toString()

        if (kritik.isBlank()){
            tietBagikanKritik.setError("Tidak Boleh Kosong")
        } else if (saran.isBlank()){
            tietBagikanSaran.setError("Tidak Boleh Kosong")
        } else{
            presenter?.kirimKritikSaran(kritik, saran, namaPengirim!!, emailPengirim!!)
        }
    }

    override fun successKirim() {
        if (statuUserKirim=="1"){
            startActivity(Intent(this, DashboardHomeDosen::class.java))
        } else{
            startActivity(Intent(this, DashboardHomeMahasiswa::class.java))
        }
    }
}