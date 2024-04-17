package com.sinarkelas.ui.dashboardhome.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.DetailAgendaActivity
import com.sinarkelas.ui.dashboardhome.DetailAgendaMahasiswaActivity
import com.sinarkelas.ui.dashboardhome.fragment.model.AgendaModel
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.inflater_agenda.view.*
import kotlinx.android.synthetic.main.inflater_agenda_mahasiswa.view.*
import java.text.SimpleDateFormat

class AdapterAgendaMahasiswa(private val itemCell : MutableList<AgendaModel>) : RecyclerView.Adapter<AdapterAgendaMahasiswa.AgendaMahasiswaViewHolder>() {

    lateinit var context: Context
    private var stylingUtil : StylingUtil? = null

    class AgendaMahasiswaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaMahasiswaViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_agenda_mahasiswa, parent, false)
        val ksv = AgendaMahasiswaViewHolder(view)
        context = parent.context

        stylingUtil = StylingUtil()

        return ksv
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onBindViewHolder(holder: AgendaMahasiswaViewHolder, position: Int) {
        var pattern = "dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].tanggalAgenda))

        holder.itemView.tvTanggalAgendaMahasiswa.text = itemCell[position].jamAgenda + "," + tanggalJoinFormat
        holder.itemView.tvNamaJudulAgendaMahasiswa.text = itemCell[position].judulAgenda

        stylingUtil?.montserratBoldTextview(context, holder.itemView.tvTanggalAgendaMahasiswa)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvAgendaJudulAgendaMahasiswa)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvNamaJudulAgendaMahasiswa)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, DetailAgendaMahasiswaActivity::class.java)
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