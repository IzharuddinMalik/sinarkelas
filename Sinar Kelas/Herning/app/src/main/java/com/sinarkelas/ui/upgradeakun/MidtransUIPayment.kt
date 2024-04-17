package com.sinarkelas.ui.upgradeakun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_midtrans_u_i_payment.*
import java.text.NumberFormat
import java.util.*

class MidtransUIPayment : AppCompatActivity() {

    var kodeTrans : String? = ""
    var namaPemesan : String? = ""
    var namaPaket : String? = ""
    var metodePembayaran : String? = ""
    var noRekening : String? = ""
    var totalPembayaran : String? = ""
    private var stylingUtil : StylingUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_midtrans_u_i_payment)

        stylingUtil = StylingUtil()

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0)
        format.setCurrency(Currency.getInstance("IDR"))

        kodeTrans = intent.getStringExtra("kodeTrans")
        namaPemesan = intent.getStringExtra("namaPemesan")
        namaPaket = intent.getStringExtra("namaPaket")
        metodePembayaran = intent.getStringExtra("metodePembayaran")
        noRekening = intent.getStringExtra("nomorRekening")
        totalPembayaran = intent.getStringExtra("totalPembayaran")

        tvNomorTransaksiKonfirmasiPembayaran.text = kodeTrans
        tvNamaDosenKonfirmasiPembayaran.text = namaPemesan
        tvNamaPaketKonfirmasiPembayaran.text = namaPaket
        tvMetodePembayaranKonfirmasiPembayaran.text = metodePembayaran
        tvNoRekeningKonfirmasiPembayaran.text = noRekening
        tvTotalKonfirmasiPembayaran.text = format.format(totalPembayaran!!.toDouble())

        stylingUtil?.montserratBoldTextview(this, tvNomorTransaksiKonfirmasiPembayaran)
        stylingUtil?.montserratBoldTextview(this, tvNamaDosenKonfirmasiPembayaran)
        stylingUtil?.montserratBoldTextview(this, tvNamaPaketKonfirmasiPembayaran)
        stylingUtil?.montserratBoldTextview(this, tvMetodePembayaranKonfirmasiPembayaran)
        stylingUtil?.montserratBoldTextview(this, tvNoRekeningKonfirmasiPembayaran)
        stylingUtil?.montserratBoldTextview(this, tvTotalKonfirmasiPembayaran)

        stylingUtil?.montserratRegularTextview(this, tvJudulNomorTransaksiKonfirmasiPembayaran)
        stylingUtil?.montserratRegularTextview(this, tvJudulNamaDosenKonfirmasiPembayaran)
        stylingUtil?.montserratRegularTextview(this, tvJudulNamaPaketKonfirmasiPembayaran)
        stylingUtil?.montserratRegularTextview(this, tvJudulMetodePembayaranKonfirmasiPembayaran)
        stylingUtil?.montserratRegularTextview(this, tvJudulNoRekeningKonfirmasiPembayaran)
        stylingUtil?.montserratRegularTextview(this, tvJudulTotalKonfirmasiPembayaran)

        cvKonfirmasiPembayaran.setOnClickListener {
            var intent = Intent(this, DashboardHomeDosen::class.java)
            startActivity(intent)
        }

    }
}