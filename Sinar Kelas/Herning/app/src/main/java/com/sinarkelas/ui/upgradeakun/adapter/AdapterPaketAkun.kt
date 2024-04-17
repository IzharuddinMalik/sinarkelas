package com.sinarkelas.ui.upgradeakun.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.upgradeakun.BeliPaketAkunActivity
import com.sinarkelas.ui.upgradeakun.model.PaketAkunModel
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.inflater_list_paketakun.view.*
import java.text.NumberFormat
import java.util.*

class AdapterPaketAkun(private val itemCell : MutableList<PaketAkunModel>) : RecyclerView.Adapter<AdapterPaketAkun.PaketViewHolder>() {

    private lateinit var context : Context
    private var stylingUtil : StylingUtil? = null

    class PaketViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaketViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_paketakun, parent, false)
        val ksv = PaketViewHolder(view)
        context = parent.context
        stylingUtil = StylingUtil()
        return ksv
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onBindViewHolder(holder: PaketViewHolder, position: Int) {

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.setMaximumFractionDigits(0)
        format.setCurrency(Currency.getInstance("IDR"))

        holder.itemView.tvNamaPaketAkun.text = itemCell[position].namaPaket
        holder.itemView.tvHargaAsliPaketAkun.text = format.format(itemCell[position].hargaPaket!!.toDouble())
        holder.itemView.tvHargaAsliPaketAkun.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        var hargaPromo = Integer.parseInt(itemCell[position].hargaPaket) * 10 / 100
        var afterPromo = Integer.parseInt(itemCell[position].hargaPaket) - hargaPromo
        holder.itemView.tvHargaPromoPaketAkun.text = format.format(afterPromo.toDouble())

        holder.itemView.tvDeskripsiPakaetAkun.text = itemCell[position].deskripsiPaket

        holder.itemView.cvBeliPaketAkun.setOnClickListener {
            var intent = Intent(context, BeliPaketAkunActivity::class.java)
            var sharedPreferences : SharedPreferences = context.getSharedPreferences("paketSession", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putString("idPaket", itemCell[position].idPaket)
            editor.putString("namaPaket", itemCell[position].namaPaket)
            editor.putString("hargaPaket", afterPromo.toString())
            editor.commit()
            context.startActivity(intent)
        }

        stylingUtil?.montserratBoldTextview(context, holder.itemView.tvNamaPaketAkun)
        stylingUtil?.montserratBoldTextview(context, holder.itemView.tvHargaPromoPaketAkun)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvHargaAsliPaketAkun)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvDeskripsiPakaetAkun)
    }

}