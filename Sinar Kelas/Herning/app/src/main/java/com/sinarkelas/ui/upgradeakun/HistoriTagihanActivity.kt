package com.sinarkelas.ui.upgradeakun

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.HistoriTagihanContract
import com.sinarkelas.presenter.HistoriTagihanPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.ui.upgradeakun.adapter.AdapterHistoriTagihan
import com.sinarkelas.ui.upgradeakun.model.HistoriBillingModel
import kotlinx.android.synthetic.main.activity_histori_tagihan.*

class HistoriTagihanActivity : AppCompatActivity(), HistoriTagihanContract.historiTagihanView {

    private var presenter : HistoriTagihanContract.historiTagihanPresenter? = null
    var idDosen : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histori_tagihan)

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")

        presenter = HistoriTagihanPresenter(this)
        presenter?.sendListHistori(idDosen!!)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        svListTagihan.hideShimmerAdapter()
    }

    override fun showLoading() {
        svListTagihan.showShimmerAdapter()
    }

    override fun getListHistori(data: MutableList<HistoriBillingModel>) {
        svListTagihan.apply {
            svListTagihan.layoutManager = LinearLayoutManager(this@HistoriTagihanActivity)
            svListTagihan.adapter = AdapterHistoriTagihan(data)
        }
    }

    @Override
    override fun onBackPressed() {
        startActivity(Intent(this, DashboardHomeDosen::class.java)).apply {
            finish()
        }
    }
}