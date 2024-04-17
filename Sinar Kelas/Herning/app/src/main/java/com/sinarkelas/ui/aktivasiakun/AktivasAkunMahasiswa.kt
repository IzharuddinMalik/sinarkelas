package com.sinarkelas.ui.aktivasiakun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinarkelas.R
import com.sinarkelas.ui.login.LoginMahasiswaActivity
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_aktivas_akun_mahasiswa.*

class AktivasAkunMahasiswa : AppCompatActivity() {

    var namaMahasiswa : String? = ""
    var stylingUtil : StylingUtil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktivas_akun_mahasiswa)

        stylingUtil = StylingUtil()

        namaMahasiswa = intent.getStringExtra("namaMahasiswa")

        tvNamaRegisterMahasiswa.text = namaMahasiswa

        stylingUtil?.montserratRegularTextview(this, tvJudulSelamatRegisMahasiswa)
        stylingUtil?.montserratBoldTextview(this, tvNamaRegisterMahasiswa)
        stylingUtil?.montserratBoldTextview(this, tvJudulSelamatRegisMahasiswa2)
        stylingUtil?.montserratRegularTextview(this, tvJudulSelamatRegisMahasiswa3)

        cvSelamatRegisMahasiswaLogin.setOnClickListener {
            startActivity(Intent(this, LoginMahasiswaActivity::class.java)).apply {
                finish()
            }
        }
    }
}