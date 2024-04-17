package com.sinarkelas.ui.aktivasiakun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinarkelas.R
import com.sinarkelas.ui.login.LoginDosenActivity
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_aktivasi_akun.*

class AktivasiAkun : AppCompatActivity() {

    var namaDosen : String? = ""
    var stylingUtil : StylingUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktivasi_akun)

        stylingUtil = StylingUtil()

        namaDosen = intent.getStringExtra("namaDosen")

        tvNamaRegisterDosen.text = namaDosen

        stylingUtil?.montserratBoldTextview(this, tvNamaRegisterDosen)
        stylingUtil?.montserratBoldTextview(this, tvJudulSelamatRegisDosen2)

        stylingUtil?.montserratRegularTextview(this, tvJudulSelamatRegisDosen)
        stylingUtil?.montserratRegularTextview(this, tvJudulSelamatRegisDosen3)

        cvSelamatRegisDosenLogin.setOnClickListener {
            startActivity(Intent(this, LoginDosenActivity::class.java)).apply {
                finish()
            }
        }
    }
}