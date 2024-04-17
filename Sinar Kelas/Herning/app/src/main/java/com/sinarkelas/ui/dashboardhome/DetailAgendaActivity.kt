package com.sinarkelas.ui.dashboardhome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sinarkelas.R
import com.sinarkelas.contract.HapusAgendaContract
import com.sinarkelas.presenter.HapusAgendaPresenter
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_detail_agenda.*
import java.text.SimpleDateFormat

class DetailAgendaActivity : AppCompatActivity(), HapusAgendaContract.hapusAgendaView {

    private var presenterHapusAgenda : HapusAgendaContract.hapusAgendaPresenter? = null

    var idAgenda : String? = ""
    var namaAgenda : String? = ""
    var deskripsiAgenda : String? = ""
    var tanggalAgenda : String? = ""
    var jamAgenda : String? = ""
    var tanggalDibuat : String? = ""
    private var stylingUtil : StylingUtil? = null

    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_agenda)

        presenterHapusAgenda =
            HapusAgendaPresenter(this)
        stylingUtil = StylingUtil()

        idAgenda = intent.getStringExtra("idAgenda")
        namaAgenda = intent.getStringExtra("judulAgenda")
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

        tvDetailNamaAgenda.text = namaAgenda
        tvDetailDeskripsiAgenda.text = deskripsiAgenda
        tvDetailTanggalAgenda.text = tanggalAgendaFormat
        tvDetailJamAgenda.text = jamAgenda
        tvDetailTanggalDibuatAgenda.text = tanggalDibuatFormat

        stylingUtil?.montserratBoldTextview(this, tvDetailTanggalDibuatAgenda)
        stylingUtil?.montserratBoldTextview(this, tvJudulDetailNamaAgenda)
        stylingUtil?.montserratBoldTextview(this, tvJudulDetailDeskripsiAgenda)
        stylingUtil?.montserratBoldTextview(this, tvJudulDetailTanggalAgenda)
        stylingUtil?.montserratBoldTextview(this, tvJudulDetailJamAgenda)

        stylingUtil?.montserratRegularTextview(this, tvDetailNamaAgenda)
        stylingUtil?.montserratRegularTextview(this, tvDetailDeskripsiAgenda)
        stylingUtil?.montserratRegularTextview(this, tvDetailTanggalAgenda)
        stylingUtil?.montserratRegularTextview(this, tvDetailJamAgenda)

        ivBackDetailAgenda.setOnClickListener {
            startActivity(Intent(this, GetListAgendaActivity::class.java)).apply {
                finish()
            }
        }

        llHapusAgenda.setOnClickListener {
            presenterHapusAgenda?.sendHapusAgenda(idAgenda!!)
        }
    }

    override fun showLoadingHapus() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoadingHapus() {
        progressDialog.dialog.dismiss()
    }

    override fun showToastHapus(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun successHapus() {
        startActivity(Intent(this, GetListAgendaActivity::class.java)).apply {
            finish()
        }
    }
}