package com.sinarkelas.ui.upgradeakun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinarkelas.R
import com.sinarkelas.contract.DetailVerifikasiPembayaranContract
import com.sinarkelas.presenter.DetailVerifikasiPembayaranPresenter
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_sukses_konfirmasi_pembayaran.*
import kotlinx.android.synthetic.main.inflater_list_paketakun.*
import java.text.SimpleDateFormat

class VerifikasiKonfirmasiPembayaranActivity : AppCompatActivity(), DetailVerifikasiPembayaranContract.detailVerifikasiPembayaranView {

    var namaPengirim : String? = ""
    var createAt : String? = ""
    var idVerifikasi : String? = ""
    var idBilling : String? = ""
    var stylingUtil : StylingUtil? = null
    private var presenterDetail : DetailVerifikasiPembayaranContract.detailVerifikasiPembayaranPresenter? = null

    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sukses_konfirmasi_pembayaran)

        stylingUtil = StylingUtil()
        presenterDetail = DetailVerifikasiPembayaranPresenter(this)

        idVerifikasi = intent.getStringExtra("idVerifikasi")

        if (idVerifikasi == "1"){
            idBilling = intent.getStringExtra("idBilling")
            presenterDetail?.sendDetailVerifikasiPembayaran(idBilling!!)
        } else{
            namaPengirim = intent.getStringExtra("namaPengirim")
            createAt = intent.getStringExtra("createAt")

            var pattern = "dd MMMM yyyy"
            var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
            var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
            var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(createAt))

            tvNamaDosenPembayaranVerifikasi.text = namaPengirim
            tvTanggalPembayaranVerifikasi.text = tanggalJoinFormat
        }

        stylingUtil?.montserratBoldTextview(this, tvJudulPembayaranVerifikasi)
        stylingUtil?.montserratBoldTextview(this, tvNamaDosenPembayaranVerifikasi)
        stylingUtil?.montserratBoldTextview(this, tvTanggalPembayaranVerifikasi)

        stylingUtil?.montserratRegularTextview(this, tvJudulPembayaranVerifikasiSelamat)
        stylingUtil?.montserratRegularTextview(this, tvJudulPembayaranVerifikasi2)
        stylingUtil?.montserratRegularTextview(this, tvJudulPembayaranVerifikasi3)
        stylingUtil?.montserratRegularTextview(this, tvJudulTanggalPembayaranVerifikasi)

        cvKembaliPembayaranVerifikasi.setOnClickListener {
            startActivity(Intent(this, HistoriTagihanActivity::class.java)).apply {
                finish()
            }
        }
    }

    override fun getDetail(namaPengirim: String, createAt: String, statusTransfer: String) {
        var pattern = "dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(createAt))

        tvNamaDosenPembayaranVerifikasi.text = namaPengirim
        tvTanggalPembayaranVerifikasi.text = tanggalJoinFormat
    }

    override fun showDialogLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideDialogLoading() {
        progressDialog.dialog.dismiss()
    }
}