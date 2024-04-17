package com.sinarkelas.ui.notifikasi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.GetNotifikasiContract
import com.sinarkelas.presenter.GetNotifikasiPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.ui.dashboardhome.DashboardHomeMahasiswa
import com.sinarkelas.ui.notifikasi.adapter.AdapterNotifikasi
import com.sinarkelas.ui.notifikasi.model.NotifikasiModel
import kotlinx.android.synthetic.main.activity_notifikasi.*

class NotifikasiActivity : AppCompatActivity(), GetNotifikasiContract.getNotifView {

    private var presenter : GetNotifikasiContract.getNotifPresenter? = null
    var statusUser : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifikasi)

        presenter = GetNotifikasiPresenter(this)

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        statusUser = sharedPreferences.getString("statusUser", "")
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        if (statusUser == "1"){
            presenter?.sendGetNotif(idDosen!!)
        } else {
            presenter?.sendGetNotif(idMahasiswa!!)
        }

        ivBackListNotifikasi.setOnClickListener {
            if (statusUser == "1"){
                startActivity(Intent(this, DashboardHomeDosen::class.java)).apply {
                    finish()
                }
            } else{
                startActivity(Intent(this, DashboardHomeMahasiswa::class.java)).apply {
                    finish()
                }
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        svListNotifikasi.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListNotifikasi.hideShimmerAdapter()
    }

    override fun getListNotif(data: MutableList<NotifikasiModel>) {
        svListNotifikasi.apply {
            svListNotifikasi.layoutManager = LinearLayoutManager(this@NotifikasiActivity)
            svListNotifikasi.adapter = AdapterNotifikasi(data)
        }
    }

    @Override
    override fun onBackPressed() {
        if (statusUser == "1"){
            startActivity(Intent(this, DashboardHomeDosen::class.java)).apply {
                finish()
            }
        } else{
            startActivity(Intent(this, DashboardHomeMahasiswa::class.java)).apply {
                finish()
            }
        }
    }
}