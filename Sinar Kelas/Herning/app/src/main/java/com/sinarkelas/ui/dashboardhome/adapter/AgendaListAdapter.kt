package com.sinarkelas.ui.dashboardhome.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.DetailAgendaActivity
import com.sinarkelas.ui.dashboardhome.fragment.model.AgendaModel
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.inflater_agenda.view.*
import java.text.SimpleDateFormat

class AgendaListAdapter(private val itemCell : MutableList<AgendaModel>) : RecyclerView.Adapter<AgendaListAdapter.AgendaViewHolder>() {

    lateinit var context : Context
    private var stylingUtil : StylingUtil? = null

    class AgendaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_agenda, parent, false)
        val ksv = AgendaViewHolder(view)
        context = parent.context

        stylingUtil = StylingUtil()

        return ksv
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onBindViewHolder(holder: AgendaViewHolder, position: Int) {

        var pattern = "dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].tanggalAgenda))

        holder.itemView.tvTanggalAgenda.text = itemCell[position].jamAgenda + "," + tanggalJoinFormat
        holder.itemView.tvNamaJudulAgenda.text = itemCell[position].judulAgenda

        stylingUtil?.montserratBoldTextview(context, holder.itemView.tvTanggalAgenda)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvAgendaJudulAgenda)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvNamaJudulAgenda)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, DetailAgendaActivity::class.java)
            intent.putExtra("idAgenda", itemCell[position].idAgenda)
            intent.putExtra("judulAgenda", itemCell[position].judulAgenda)
            intent.putExtra("deskripsiAgenda", itemCell[position].deskripsiAgenda)
            intent.putExtra("tanggalAgenda", itemCell[position].tanggalAgenda)
            intent.putExtra("jamAgenda", itemCell[position].jamAgenda)
            intent.putExtra("tanggalDibuat", itemCell[position].tanggalDibuat)
            context.startActivity(intent)
        }
    }

}