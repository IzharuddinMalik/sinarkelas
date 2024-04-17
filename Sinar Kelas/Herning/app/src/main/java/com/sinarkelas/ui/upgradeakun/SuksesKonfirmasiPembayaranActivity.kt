package com.sinarkelas.ui.upgradeakun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinarkelas.R
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_sukses_konfirmasi_pembayaran2.*

class SuksesKonfirmasiPembayaranActivity : AppCompatActivity() {

    private var namaPengirim : String? = ""
    private var stylingUtil : StylingUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sukses_konfirmasi_pembayaran2)

        stylingUtil = StylingUtil()

        namaPengirim = intent.getStringExtra("namaPengirim")

        tvNamaDosenPembayaranSukses.text = namaPengirim

        stylingUtil?.montserratBoldTextview(this, tvJudulPembayaranSukses)
        stylingUtil?.montserratBoldTextview(this, tvNamaDosenPembayaranSukses)

        stylingUtil?.montserratRegularTextview(this, tvJudulPembayaranSukses2)
        stylingUtil?.montserratRegularTextview(this, tvJudulPembayaranSukses3)
        stylingUtil?.montserratRegularTextview(this, tvJudulPembayaranSuksesSelamat)

        cvKembaliPembayaranSukses.setOnClickListener {
            startActivity(Intent(this, HistoriTagihanActivity::class.java)).apply {
                finish()
            }
        }
    }
}