package com.sinarkelas.ui.dashboardhome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.HapusAgendaMahasiswaContract
import com.sinarkelas.presenter.HapusAgendaMahasiswaPresenter
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_detail_agenda.*
import kotlinx.android.synthetic.main.activity_detail_agenda_mahasiswa.*
import java.text.SimpleDateFormat

class DetailAgendaMahasiswaActivity : AppCompatActivity(), HapusAgendaMahasiswaContract.hapusAgendaMahasiswaView {

    var idAgenda : String? = ""
    var judulAgenda : String? = ""
    var deskripsiAgenda : String? = ""
    var tanggalAgenda : String? = ""
    var jamAgenda : String? = ""
    var tanggalDibuat : String? = ""
    var stylingUtil : StylingUtil? = null

    private var progressDialog = CustomProgressDialog()

    private var presenterHapus : HapusAgendaMahasiswaContract.hapusAgendaMahasiswaPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_agenda_mahasiswa)

        presenterHapus = HapusAgendaMahasiswaPresenter(this)

        stylingUtil = StylingUtil()

        idAgenda = intent.getStringExtra("idAgenda")
        judulAgenda = intent.getStringExtra("judulAgenda")
        deskripsiAgenda = intent.getStringExtra("deskripsiAgenda")
        tanggalAgenda = intent.getStringExtra("tanggalAgenda")
        jamAgenda = intent.getStringExtra("jamAgenda")
        tanggalDibuat = intent.getStringExtra("tanggalDibuat")

        var pattern = "dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
        var tanggalDibuatFormat = simpleDateFormat.format(tanggalFromAPI.parse(tanggalDibuat))

        var pattern2 = "dd MMMM yyyy"
        var simpleDateFormat2 : SimpleDateFormat = SimpleDateFormat(pattern2)
        var tanggalFromAPI2 = SimpleDateFormat("yyyy-MM-dd")
        var tanggalAgendaFormat = simpleDateFormat2.format(tanggalFromAPI2.parse(tanggalAgenda))

        tvDetailNamaAgendaMahasiswa.text = judulAgenda
        tvDetailDeskripsiAgendaMahasiswa.text = deskripsiAgenda
        tvDetailTanggalAgendaMahasiswa.text = tanggalAgendaFormat
        tvDetailJamAgendaMahasiswa.text = jamAgenda
        tvDetailTanggalDibuatAgendaMahasiswa.text = tanggalDibuatFormat

        stylingUtil?.montserratBoldTextview(this, tvDetailTanggalDibuatAgendaMahasiswa)
        stylingUtil?.montserratBoldTextview(this, tvJudulDetailNamaAgendaMahasiswa)
        stylingUtil?.montserratBoldTextview(this, tvJudulDetailDeskripsiAgendaMahasiswa)
        stylingUtil?.montserratBoldTextview(this, tvJudulDetailTanggalAgendaMahasiswa)
        stylingUtil?.montserratBoldTextview(this, tvJudulDetailJamAgendaMahasiswa)

        stylingUtil?.montserratRegularTextview(this, tvDetailNamaAgendaMahasiswa)
        stylingUtil?.montserratRegularTextview(this, tvDetailDeskripsiAgendaMahasiswa)
        stylingUtil?.montserratRegularTextview(this, tvDetailTanggalAgendaMahasiswa)
        stylingUtil?.montserratRegularTextview(this, tvDetailJamAgendaMahasiswa)

        llHapusAgendaMahasiswa.setOnClickListener {
            presenterHapus?.sendHapus(idAgenda!!)
        }
    }

    override fun showToastHapusAgenda(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun successHapus() {
        startActivity(Intent(this, GetListAgendaMahasiswaActivity::class.java))
        finish()
    }
}