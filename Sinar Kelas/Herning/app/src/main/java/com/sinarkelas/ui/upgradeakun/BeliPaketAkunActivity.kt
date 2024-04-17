package com.sinarkelas.ui.upgradeakun

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.PaymentMetodeContract
import com.sinarkelas.presenter.PaymentMetodePresenter
import com.sinarkelas.ui.upgradeakun.adapter.AdapterPayment
import com.sinarkelas.ui.upgradeakun.model.PaymentModel
import kotlinx.android.synthetic.main.activity_beli_paket_akun.*

class BeliPaketAkunActivity : AppCompatActivity(), PaymentMetodeContract.paymentMetodeView {

    private var presenterPay : PaymentMetodeContract.paymentMetodePresent? = null
    var idDosen : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beli_paket_akun)

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")

        presenterPay = PaymentMetodePresenter(this)
        presenterPay?.sendPayment(idDosen!!)

    }

    override fun showToast(message: String) {

    }

    override fun hideLoading() {
        svListPayment.hideShimmerAdapter()
    }

    override fun showLoading() {
        svListPayment.showShimmerAdapter()
    }

    override fun getPayment(data: MutableList<PaymentModel>) {
        svListPayment.apply {
            svListPayment.layoutManager = LinearLayoutManager(this@BeliPaketAkunActivity)
            svListPayment.adapter = AdapterPayment(data)
        }
    }
}