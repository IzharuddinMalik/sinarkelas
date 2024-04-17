package com.sinarkelas.ui.notifikasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sinarkelas.R
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_detail_notifikasi.*
import java.text.SimpleDateFormat

class DetailNotifikasiActivity : AppCompatActivity() {

    private var stylingUtil : StylingUtil? = null
    var namaAdmin : String? = ""
    var pesanNotif : String? = ""
    var gambarNotif : String? = ""
    var tanggalNotif : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_notifikasi)

        stylingUtil = StylingUtil()

        namaAdmin = intent.getStringExtra("namaAdmin")
        pesanNotif = intent.getStringExtra("pesanNotif")
        gambarNotif = intent.getStringExtra("gambarNotif")
        tanggalNotif = intent.getStringExtra("tanggalNotif")

        var pattern = "HH:mm:ss dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(tanggalNotif))

        tvTanggalDetailNotifikasi.text = tanggalJoinFormat
        tvNamaAdminDetailNotifikasi.text = namaAdmin
        tvPesanDetailNotifikasi.text = pesanNotif

//        Glide.with(this).load("https://sinarcenter.id/sinarkelasimage/" + gambarNotif).into(ivGambarNotif)

        stylingUtil?.montserratBoldTextview(this, tvNamaAdminDetailNotifikasi)
        stylingUtil?.montserratRegularTextview(this, tvTanggalDetailNotifikasi)
        stylingUtil?.montserratRegularTextview(this, tvPesanDetailNotifikasi)

        ivBackDetailNotifikasi.setOnClickListener {
            startActivity(Intent(this, NotifikasiActivity::class.java)).apply {
                finish()
            }
        }
    }
}