package com.sinarkelas.ui.upgradeakun.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.upgradeakun.DetailHistoriTagihanActivity
import com.sinarkelas.ui.upgradeakun.SuksesKonfirmasiPembayaranActivity
import com.sinarkelas.ui.upgradeakun.VerifikasiKonfirmasiPembayaranActivity
import com.sinarkelas.ui.upgradeakun.model.HistoriBillingModel
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.inflater_list_tagihan.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AdapterHistoriTagihan(private val itemCell : MutableList<HistoriBillingModel>) : RecyclerView.Adapter<AdapterHistoriTagihan.TagihanViewHolder>() {

    lateinit var context: Context
    private var stylingUtil : StylingUtil? = null

    class TagihanViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TagihanViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_tagihan, parent, false)
        val ksv = TagihanViewHolder(view)
        context = parent.context
        stylingUtil = StylingUtil()
        return ksv
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onBindViewHolder(holder: AdapterHistoriTagihan.TagihanViewHolder, position: Int) {

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0)
        format.setCurrency(Currency.getInstance("IDR"))

        var pattern = "dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].dateBill))

        holder.itemView.tvKodeBillingHistori.text = itemCell[position].kodeBilling
        holder.itemView.tvTanggalBillingHistori.text = tanggalJoinFormat
        holder.itemView.tvNamaDosenBillingHistori.text = itemCell[position].namaDosen
        holder.itemView.tvNamaPayBillingHistori.text = itemCell[position].namaPay
        holder.itemView.tvTotalPayBillingHistori.text = format.format(itemCell[position].totalBill!!.toDouble())

        if (itemCell[position].statusBill.equals("Upload Bukti Pembayaran")){
            holder.itemView.ivStatusBillingHistori.setImageDrawable(context.resources.getDrawable(R.drawable.ic_pending))
            holder.itemView.tvStatusBillingHistori.setText("Upload Bukti Pembayaran")
            holder.itemView.tvStatusBillingHistori.setTextColor(context.resources.getColor(R.color.maroonSinar))

            holder.itemView.setOnClickListener {
                var intent = Intent(context, DetailHistoriTagihanActivity::class.java)
                intent.putExtra("idBilling", itemCell[position].idBilling)
                context.startActivity(intent)
            }

        } else if (itemCell[position].statusBill.equals("Sedang Diverifikasi")){
            holder.itemView.ivStatusBillingHistori.setImageDrawable(context.resources.getDrawable(R.drawable.ic_pending))
            holder.itemView.tvStatusBillingHistori.setText("Sedang Diverifikasi")
            holder.itemView.tvStatusBillingHistori.setTextColor(context.resources.getColor(R.color.maroonSinar))

            holder.itemView.setOnClickListener {
                var intent = Intent(context, VerifikasiKonfirmasiPembayaranActivity::class.java)
                intent.putExtra("idBilling", itemCell[position].idBilling)
                intent.putExtra("idVerifikasi", "1")
                context.startActivity(intent)
            }
        } else{
            holder.itemView.ivStatusBillingHistori.setImageDrawable(context.resources.getDrawable(R.drawable.ic_checkgreen))
            holder.itemView.tvStatusBillingHistori.setText("Sukses")
            holder.itemView.tvStatusBillingHistori.setTextColor(context.resources.getColor(R.color.greenColor))

            holder.itemView.setOnClickListener {
                var intent = Intent(context, SuksesKonfirmasiPembayaranActivity::class.java)
                intent.putExtra("namaPengirim", itemCell[position].namaDosen)
                context.startActivity(intent)
            }
        }

        stylingUtil?.montserratBoldTextview(context, holder.itemView.tvKodeBillingHistori)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvTanggalBillingHistori)
        stylingUtil?.montserratBoldTextview(context, holder.itemView.tvNamaDosenBillingHistori)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvNamaPayBillingHistori)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvTotalPayBillingHistori)
    }
}