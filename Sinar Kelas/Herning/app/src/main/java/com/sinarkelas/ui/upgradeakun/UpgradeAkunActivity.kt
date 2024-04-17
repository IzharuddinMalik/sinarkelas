package com.sinarkelas.ui.upgradeakun

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.PaketAkunContract
import com.sinarkelas.presenter.PaketAkunPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.ui.upgradeakun.adapter.AdapterPaketAkun
import com.sinarkelas.ui.upgradeakun.model.PaketAkunModel
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_upgrade_akun.*

class UpgradeAkunActivity : AppCompatActivity(), PaketAkunContract.paketAkunView {

    private var presenterPaket : PaketAkunContract.paketAkunPresenter? = null
    var idDosen : String? = ""
    var stylingUtil : StylingUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upgrade_akun)

        presenterPaket = PaketAkunPresenter(this)
        stylingUtil = StylingUtil()

        stylingUtil?.montserratBoldTextview(this, tvJudulUpgradeAkun)
        stylingUtil?.montserratBoldTextview(this, tvJudulPaketAkunHerning)

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")

        presenterPaket?.sendPaket(idDosen!!)

        ivBackUpgradeAkun.setOnClickListener {
            startActivity(Intent(this, DashboardHomeDosen::class.java))
        }
    }

    override fun showToast(message: String) {

    }

    override fun showLoading() {
        svListPaketAkun.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListPaketAkun.hideShimmerAdapter()
    }

    override fun getPaket(data: MutableList<PaketAkunModel>) {
        svListPaketAkun.apply {
            svListPaketAkun.layoutManager = LinearLayoutManager(this@UpgradeAkunActivity)
            svListPaketAkun.adapter = AdapterPaketAkun(data)
        }
    }
}