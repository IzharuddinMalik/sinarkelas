package com.sinarkelas.ui.upgradeakun

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinarkelas.R
import com.sinarkelas.contract.DetailTagihanContract
import com.sinarkelas.presenter.DetailTagihanPresenter
import com.sinarkelas.util.CustomProgressDialog
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.activity_detail_histori_tagihan.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailHistoriTagihanActivity : AppCompatActivity(), DetailTagihanContract.detailTagihanView {

    private var presenterDetailHistori : DetailTagihanContract.detailTagihanPresenter? = null
    private var stylingUtil : StylingUtil? = null
    var idDosen : String? = ""
    var idBillingHistori : String? = ""

    private var progressDialog = CustomProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_histori_tagihan)

        var sharedPreferences : SharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")

        idBillingHistori = intent.getStringExtra("idBilling")
        stylingUtil = StylingUtil()

        presenterDetailHistori =
            DetailTagihanPresenter(this)
        presenterDetailHistori?.sendDetailTagihan( idBillingHistori!!, idDosen!!)

        cvUploadBuktiPembayaran.setOnClickListener {
            var intent = Intent(this, UploadBuktiPembayaranActivity::class.java)
            intent.putExtra("idBilling", idBillingHistori)
            startActivity(intent)
        }
    }

    override fun showToast(message: String) {

    }

    override fun showLoading() {
        progressDialog.show(this, "Harap tunggu...")
    }

    override fun hideLoading() {
        progressDialog.dialog.dismiss()
    }

    override fun getDetailHistori(
        idBilling: String,
        kodeBilling: String,
        idDosen: String,
        namaDosen: String,
        namaPay: String,
        namaPaket: String,
        totalBill: String,
        dateBill: String,
        overdueDate: String,
        statusBill: String
    ) {

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0)
        format.setCurrency(Currency.getInstance("IDR"))

        var pattern = "dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(dateBill))

        tvTanggalDetailHistori.text = tanggalJoinFormat
        tvKodeReferensiDetailHistori.text = kodeBilling
        tvNamaDosenDetailHistori.text = namaDosen
        tvPaketDetailHistori.text = namaPaket
        tvMetodePembayaranDetailHistori.text = namaPay
        tvTotalPembayaranDetailHistori.text = format.format(totalBill.toDouble())

        if (statusBill.equals("Upload Bukti Pembayaran")){
            ivStatusDetailHistori.setImageDrawable(resources.getDrawable(R.drawable.ic_pending))
            tvStatusDetailHistori.text = "Upload Bukti Pembayaran"
            tvStatusDetailHistori.setTextColor(resources.getColor(R.color.maroonSinar))
        } else if (statusBill.equals("Sedang Diverifikasi")){
            ivStatusDetailHistori.setImageDrawable(resources.getDrawable(R.drawable.ic_pending))
            tvStatusDetailHistori.text = "Sedang Diverifikasi"
            tvStatusDetailHistori.setTextColor(resources.getColor(R.color.maroonSinar))
        } else{
            ivStatusDetailHistori.setImageDrawable(resources.getDrawable(R.drawable.ic_checkgreen))
            tvStatusDetailHistori.text = "Sukses"
            tvStatusDetailHistori.setTextColor(resources.getColor(R.color.greenColor))
        }

        stylingUtil?.montserratBoldTextview(this, tvJudulKodeReferensiDetailHistori)
        stylingUtil?.montserratBoldTextview(this, tvJudulNamaDosenDetailHistori)
        stylingUtil?.montserratBoldTextview(this, tvJudulMetodePembayaranDetailHistori)
        stylingUtil?.montserratBoldTextview(this, tvJudulPaketDetailHistori)
        stylingUtil?.montserratBoldTextview(this, tvTotalPembayaranDetailHistori)

        stylingUtil?.montserratRegularTextview(this, tvTanggalDetailHistori)
        stylingUtil?.montserratRegularTextview(this, tvKodeReferensiDetailHistori)
        stylingUtil?.montserratRegularTextview(this, tvNamaDosenDetailHistori)
        stylingUtil?.montserratRegularTextview(this, tvPaketDetailHistori)
        stylingUtil?.montserratRegularTextview(this, tvMetodePembayaranDetailHistori)
        stylingUtil?.montserratRegularTextview(this, tvJudulTotalPembayaranDetailHistori)
    }
}