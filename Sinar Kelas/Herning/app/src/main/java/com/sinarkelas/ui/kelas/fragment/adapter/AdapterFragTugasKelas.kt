package com.sinarkelas.ui.kelas.fragment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.fragment.model.ListTugasModel
import com.sinarkelas.ui.kelas.DetailTugasKelasActivity
import kotlinx.android.synthetic.main.inflater_tugas_kelas.view.*
import java.text.SimpleDateFormat

class AdapterFragTugasKelas(private val itemCell : MutableList<ListTugasModel>) : RecyclerView.Adapter<AdapterFragTugasKelas.TugasViewHolder>() {

    lateinit var context: Context

    class TugasViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TugasViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_tugas_kelas, parent, false)
        val ksv = TugasViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: TugasViewHolder, position: Int) {
        var pattern = "HH:mm:ss, dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].createAt))

        holder.itemView.tvListJudulTugasKelas.text = itemCell[position].judulTugas
        holder.itemView.tvListTanggalTugasKelas.text = tanggalJoinFormat

        holder.itemView.setOnClickListener {
            var intent = Intent(context, DetailTugasKelasActivity::class.java)
            intent.putExtra("idTugas", itemCell[position].idTugas)
            context.startActivity(intent)
        }
    }
}