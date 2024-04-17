package com.sinarkelas.ui.upgradeakun.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.midtrans.Config
import com.midtrans.ConfigFactory
import com.midtrans.service.MidtransCoreApi
import com.sinarkelas.R
import com.sinarkelas.contract.BayarBillingContract
import com.sinarkelas.contract.DosenGetProfileContract
import com.sinarkelas.presenter.BayarBillingPresenter
import com.sinarkelas.presenter.DosenGetProfilePresenter
import com.sinarkelas.ui.upgradeakun.MidtransUIPayment
import com.sinarkelas.ui.upgradeakun.model.PaymentModel
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.inflater_metode_pembayaran.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap

class AdapterPayment(private val itemCell : MutableList<PaymentModel>) : RecyclerView.Adapter<AdapterPayment.PaymentViewHolder>(), DosenGetProfileContract.dosenProfileView, BayarBillingContract.bayarBillingView {

    private lateinit var context: Context
    private var stylingUtil : StylingUtil? = null
    var idPaket : String? = ""
    var namaPaket : String? = ""
    var hargaPaket : String? = ""
    var idDosen : String? = ""
    var namaDosenAdapter : String? = ""
    var emailDosenAdapter : String? = ""
    private var presenterDosen : DosenGetProfileContract.dosenProfilePresenter? = null
    private var presenterBilling : BayarBillingContract.bayarBillingPresenter? = null

    class PaymentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PaymentViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_metode_pembayaran, parent, false)
        val ksv = PaymentViewHolder(view)
        context = parent.context
        stylingUtil = StylingUtil()
        presenterDosen = DosenGetProfilePresenter(this)
        presenterBilling = BayarBillingPresenter(this)

        var sharedPreferences : SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")

        presenterDosen?.getProfile(idDosen!!)
        return ksv
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onBindViewHolder(holder: AdapterPayment.PaymentViewHolder, position: Int) {

        if (itemCell[position].namaPay == "GOPAY"){
            holder.itemView.ivIconPayment.setImageDrawable(context.resources.getDrawable(R.drawable.icongopay))
        } else if (itemCell[position].namaPay == "Shopeepay"){
            holder.itemView.ivIconPayment.setImageDrawable(context.resources.getDrawable(R.drawable.shopeepay))
        } else if (itemCell[position].namaPay == "BNI VA"){
            holder.itemView.ivIconPayment.setImageDrawable(context.resources.getDrawable(R.drawable.bni))
        } else if (itemCell[position].namaPay == "Permata VA") {
            holder.itemView.ivIconPayment.setImageDrawable(context.resources.getDrawable(R.drawable.permata))
        } else if (itemCell[position].namaPay == "BCA"){
            holder.itemView.ivIconPayment.setImageDrawable(context.resources.getDrawable(R.drawable.bca))
        }

        var sharedPreferences2 : SharedPreferences = context.getSharedPreferences("paketSession", Context.MODE_PRIVATE)
        idPaket = sharedPreferences2.getString("idPaket", "")
        namaPaket = sharedPreferences2.getString("namaPaket", "")
        hargaPaket = sharedPreferences2.getString("hargaPaket", "")

        holder.itemView.tvNamaPayment.text = itemCell[position].namaPay

        holder.itemView.setOnClickListener {
            if (itemCell[position].namaPay == "GOPAY"){
                Toast.makeText(context, "Saat ini belum tersedia.", Toast.LENGTH_LONG).show()
//                try {
//                    ewalletCharge("gopay", position)
//                } catch (midtransError : MidtransError){
//                    midtransError.printStackTrace()
//                }
            } else if (itemCell[position].namaPay == "Shopeepay"){
                Toast.makeText(context, "Saat ini belum tersedia.", Toast.LENGTH_LONG).show()
//                try{
//                    ewalletCharge("shopeepay", position)
//                } catch (midtransErro : MidtransError){
//                    midtransErro.printStackTrace()
//                }
            } else if (itemCell[position].namaPay == "BNI VA"){
                Toast.makeText(context, "Saat ini belum tersedia.", Toast.LENGTH_LONG).show()
//                try {
//                    bankTransfer("bni", position)
//                } catch (midtransErr : MidtransError){
//                    midtransErr.printStackTrace()
//                }
            } else if (itemCell[position].namaPay == "Permata VA"){
                Toast.makeText(context, "Saat ini belum tersedia.", Toast.LENGTH_LONG).show()
//                try {
//                    bankTransfer("permata", position)
//                } catch (midtransErr : MidtransError){
//                    midtransErr.printStackTrace()
//                }
            } else if (itemCell[position].namaPay == "BCA") {
                presenterBilling?.sendBayarBilling(idDosen!!, itemCell[position].idPay!!, idPaket!!, hargaPaket!!)
            }
        }

    }

    fun ewalletCharge(namapay : String, position: Int){

        var thread : Thread = Thread(object : Runnable{
            override fun run() {
                try {

                    if (namapay == "GOPAY"){
                        var itemDetail : HashMap<String, String> = HashMap<String, String>()
                        itemDetail.put("id", idPaket!! )
                        itemDetail.put("price", hargaPaket!!)
                        itemDetail.put("quantity", "1")
                        itemDetail.put("name", namaPaket!!)

                        Log.i("ITEMDETAILS", " === " + itemDetail.toString())

                        var coreApi : MidtransCoreApi = ConfigFactory(Config("SB-Mid-server-Lfx040_xtG_a4qYyF1SQumf_", "SB-Mid-client-uWsCQbcg_oaSUrtb", false)).coreApi
                        coreApi.apiConfig().connectionTimeout = 5
                        coreApi.apiConfig().readTimeout = 5
                        coreApi.apiConfig().writeTimeout = 5

                        var idRand : UUID = UUID.randomUUID()

                        var transactionDetail : HashMap<String, String> = HashMap()
                        transactionDetail.put("order_id", idRand.toString())
                        transactionDetail.put("gross_amount", hargaPaket!!)

                        var customerDetails : HashMap<String, String> = HashMap()
                        customerDetails.put("first_name", namaDosenAdapter!!)
                        customerDetails.put("email", emailDosenAdapter!!)

                        var chargeParams : HashMap<String, Any> = HashMap<String, Any>()
                        chargeParams.put("payment_type", namapay)
                        chargeParams.put("item_details", itemDetail)
                        chargeParams.put("customer_details", customerDetails)
                        chargeParams.put("transaction_details", transactionDetail)

                        var result : JSONObject = coreApi.chargeTransaction(chargeParams.toMap())
                        Log.i("RESULT", " === " + result)

                        if (result.getString("status_code").equals("201")){
                            var actionarr : JSONArray = result.getJSONArray("actions")
                            for (i in 0 until actionarr.length()){
                                var objAction : JSONObject = actionarr.getJSONObject(i)

                                if (objAction.getString("name").equals("deeplink-redirect")){
                                    val uris = Uri.parse(objAction.getString("url"))
                                    val intents = Intent(Intent.ACTION_VIEW, uris)
                                    val b = Bundle()
                                    b.putBoolean("new_window", true)
                                    intents.putExtras(b)
                                    context.startActivity(intents)
                                }
                            }
                        }
                    } else{
                        var itemDetail : HashMap<String, String> = HashMap<String, String>()
                        itemDetail.put("id", idPaket!! )
                        itemDetail.put("price", hargaPaket!!)
                        itemDetail.put("quantity", "1")
                        itemDetail.put("name", namaPaket!!)

                        Log.i("ITEMDETAILS", " === " + itemDetail.toString())

                        var coreApi : MidtransCoreApi = ConfigFactory(Config("SB-Mid-server-Lfx040_xtG_a4qYyF1SQumf_", "SB-Mid-client-uWsCQbcg_oaSUrtb", false)).coreApi
                        coreApi.apiConfig().connectionTimeout = 5
                        coreApi.apiConfig().readTimeout = 5
                        coreApi.apiConfig().writeTimeout = 5

                        var idRand : UUID = UUID.randomUUID()

                        var transactionDetail : HashMap<String, String> = HashMap()
                        transactionDetail.put("order_id", idRand.toString())
                        transactionDetail.put("gross_amount", hargaPaket!!)

                        var customerDetails : HashMap<String, String> = HashMap()
                        customerDetails.put("first_name", namaDosenAdapter!!)
                        customerDetails.put("email", emailDosenAdapter!!)

                        var shopeepayDetail : HashMap<String, String> = HashMap()
                        shopeepayDetail.put("callback_url", "https://midtrans.com/")

                        var chargeParams : HashMap<String, Any> = HashMap<String, Any>()
                        chargeParams.put("payment_type", namapay)
                        chargeParams.put("item_details", itemDetail)
                        chargeParams.put("customer_details", customerDetails)
                        chargeParams.put("transaction_details", transactionDetail)
                        chargeParams.put("shopeepay", shopeepayDetail)

                        var result : JSONObject = coreApi.chargeTransaction(chargeParams.toMap())
                        Log.i("RESULT", " === " + result)

                        if (result.getString("status_code").equals("201")){
                            var actionarr : JSONArray = result.getJSONArray("actions")
                            for (i in 0 until actionarr.length()){
                                var objAction : JSONObject = actionarr.getJSONObject(i)

                                if (objAction.getString("name").equals("deeplink-redirect")){
                                    val uris = Uri.parse(objAction.getString("url"))
                                    val intents = Intent(Intent.ACTION_VIEW, uris)
                                    val b = Bundle()
                                    b.putBoolean("new_window", true)
                                    intents.putExtras(b)
                                    context.startActivity(intents)
                                }
                            }
                        }
                    }

                } catch (e : Exception){
                    e.printStackTrace()
                }
            }
        })

        thread.start()
    }

    fun bankTransfer(namabank : String, position: Int){
        var thread : Thread = Thread(object : Runnable{
            override fun run() {
                try {

                    if (namabank == "BNI VA"){
                        var itemDetail : HashMap<String, String> = HashMap<String, String>()
                        itemDetail.put("id", idPaket!! )
                        itemDetail.put("price", hargaPaket!!)
                        itemDetail.put("quantity", "1")
                        itemDetail.put("name", namaPaket!!)

                        var jsonArray : JSONArray = JSONArray()
                        try {
                            jsonArray.put(itemDetail)
                        } catch (e : JSONException){
                            e.printStackTrace()
                        }

                        Log.i("ITEMDETAILS", " === " + jsonArray)

                        var coreApi : MidtransCoreApi = ConfigFactory(Config("SB-Mid-server-Lfx040_xtG_a4qYyF1SQumf_", "SB-Mid-client-uWsCQbcg_oaSUrtb", false)).coreApi
                        coreApi.apiConfig().connectionTimeout = 5
                        coreApi.apiConfig().readTimeout = 5
                        coreApi.apiConfig().writeTimeout = 5

                        var idRand : UUID = UUID.randomUUID()

                        var transactionDetail : HashMap<String, String> = HashMap()
                        transactionDetail.put("order_id", idRand.toString())
                        transactionDetail.put("gross_amount", hargaPaket!!)

                        var customerDetails : HashMap<String, String> = HashMap()
                        customerDetails.put("first_name", namaDosenAdapter!!)
                        customerDetails.put("email", emailDosenAdapter!!)

                        var bankTransferDetails : HashMap<String, String> = HashMap()
                        bankTransferDetails.put("bank", "bni")

                        var chargeParams : HashMap<String, Any> = HashMap<String, Any>()
                        chargeParams.put("payment_type", "bank_transfer")
                        chargeParams.put("customer_details", customerDetails)
                        chargeParams.put("item_details", jsonArray)
                        chargeParams.put("bank_transfer", bankTransferDetails)
                        chargeParams.put("transaction_details", transactionDetail)

                        var result : JSONObject = coreApi.chargeTransaction(chargeParams.toMap())
                        Log.i("RESULT", " === " + result)

                        if (result.getString("status_code").equals("201")){
                            var actionarr : JSONArray = result.getJSONArray("va_numbers")
                            for (i in 0 until actionarr.length()){
                                var objAction : JSONObject = actionarr.getJSONObject(i)

                                var intent = Intent(context, MidtransUIPayment::class.java)
                                intent.putExtra("va_numbers", objAction.getString("va_number"))
                                context.startActivity(intent)
                            }
                        }

                    } else{
                        var itemDetail : HashMap<String, String> = HashMap<String, String>()
                        itemDetail.put("id", idPaket!! )
                        itemDetail.put("price", hargaPaket!!)
                        itemDetail.put("quantity", "1")
                        itemDetail.put("name", namaPaket!!)

                        Log.i("ITEMDETAILS", " === " + itemDetail.toString())

                        var coreApi : MidtransCoreApi = ConfigFactory(Config("SB-Mid-server-Lfx040_xtG_a4qYyF1SQumf_", "SB-Mid-client-uWsCQbcg_oaSUrtb", false)).coreApi
                        coreApi.apiConfig().connectionTimeout = 5
                        coreApi.apiConfig().readTimeout = 5
                        coreApi.apiConfig().writeTimeout = 5

                        var idRand : UUID = UUID.randomUUID()

                        var transactionDetail : HashMap<String, String> = HashMap()
                        transactionDetail.put("order_id", idRand.toString())
                        transactionDetail.put("gross_amount", hargaPaket!!)

                        var customerDetails : HashMap<String, String> = HashMap()
                        customerDetails.put("first_name", namaDosenAdapter!!)
                        customerDetails.put("email", emailDosenAdapter!!)

                        var bankTransferDetails : HashMap<String, Any> = HashMap()
                        bankTransferDetails.put("bank", "permata")

                        var receipentBank : HashMap<String, String> = HashMap()
                        receipentBank.put("receipent_name", namaDosenAdapter!!)
                        bankTransferDetails.put("permata", receipentBank)

                        var chargeParams : HashMap<String, Any> = HashMap<String, Any>()
                        chargeParams.put("payment_type", "bank_transfer")
                        chargeParams.put("customer_details", customerDetails)
                        chargeParams.put("item_details", itemDetail)
                        chargeParams.put("bank_transfer", bankTransferDetails)
                        chargeParams.put("transaction_details", transactionDetail)

                        var result : JSONObject = coreApi.chargeTransaction(chargeParams.toMap())
                        Log.i("RESULT", " === " + result)

                        if (result.getString("status_code").equals("201")){
                            var intent = Intent(context, MidtransUIPayment::class.java)
                            intent.putExtra("va_numbers", result.getString("permata_va_number"))
                            context.startActivity(intent)
                        }
                    }

                } catch (e : Exception){
                    e.printStackTrace()
                }
            }
        })

        thread.start()
    }

    override fun showToast(message: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun getProfileDosen(namaDosen: String, emailDosen: String, fotoProfileDosen: String, endDate : String, statusAkun : String) {
        namaDosenAdapter = namaDosen
        emailDosenAdapter = emailDosen
    }

    override fun showToastBayar(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoadingBayar() {

    }

    override fun hideLoadingBayar() {

    }

    override fun successBayar(kodeTrans : String) {
        var intent = Intent(context, MidtransUIPayment::class.java)
        intent.putExtra("nomorRekening", "0374162531")
        intent.putExtra("metodePembayaran", "BCA")
        intent.putExtra("totalPembayaran", hargaPaket)
        intent.putExtra("namaPaket", namaPaket)
        intent.putExtra("namaPemesan", namaDosenAdapter)
        intent.putExtra("kodeTrans", kodeTrans)
        context.startActivity(intent)
    }

}