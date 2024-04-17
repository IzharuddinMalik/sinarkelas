package com.sinarkelas.ui.notifikasi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.notifikasi.DetailNotifikasiActivity
import com.sinarkelas.ui.notifikasi.model.NotifikasiModel
import com.sinarkelas.ui.upgradeakun.adapter.AdapterHistoriTagihan
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.inflater_list_notif.view.*
import java.text.SimpleDateFormat

class AdapterNotifikasi(private val itemCell : MutableList<NotifikasiModel>) : RecyclerView.Adapter<AdapterNotifikasi.NotifikasiViewHolder>() {

    lateinit var context: Context
    private var stylingUtil : StylingUtil? = null

    class NotifikasiViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifikasiViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_notif, parent, false)
        val ksv = NotifikasiViewHolder(view)
        context = parent.context
        stylingUtil = StylingUtil()
        return ksv
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onBindViewHolder(holder: NotifikasiViewHolder, position: Int) {

        var pattern = "dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].tanggalNotifikasi))

        holder.itemView.tvJudulNotifikasi.text = itemCell[position].pesanNotif
        holder.itemView.tvTanggalNotifikasi.text = tanggalJoinFormat

        stylingUtil?.montserratBoldTextview(context, holder.itemView.tvJudulNotifikasi)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvTanggalNotifikasi)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, DetailNotifikasiActivity::class.java)
            intent.putExtra("pesanNotif", itemCell[position].pesanNotif)
            intent.putExtra("namaAdmin", itemCell[position].namaAdmin)
            intent.putExtra("tanggalNotif", itemCell[position].tanggalNotifikasi)
            intent.putExtra("gambarNotif", itemCell[position].gambarNotif)
            context.startActivity(intent)
        }
    }

}