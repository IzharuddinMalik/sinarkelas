package com.sinarkelas.ui.kelas.fragment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.kelas.DetailPresensiKelasActivity
import com.sinarkelas.ui.kelas.model.ListPresensiKelasModel
import kotlinx.android.synthetic.main.inflater_list_presensikelas.view.*
import java.text.SimpleDateFormat

class AdapterFragPresensiKelas(private val itemCell : MutableList<ListPresensiKelasModel>) : RecyclerView.Adapter<AdapterFragPresensiKelas.PresensiViewHolder>() {

    lateinit var context: Context

    class PresensiViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresensiViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_presensikelas, parent, false)
        val ksv = PresensiViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: PresensiViewHolder, position: Int) {

        var pattern = "HH:mm:ss, dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].tanggal))

        holder.itemView.tvListTanggalPresensiKelas.text = tanggalJoinFormat
        holder.itemView.tvListJuduPresensiKelas.text = "PRESENSI KELAS - " + tanggalJoinFormat

        holder.itemView.cvFragDiskusiPresensiKelas.setOnClickListener {
            var intent = Intent(context, DetailPresensiKelasActivity::class.java)
            intent.putExtra("idPresensiKelas", itemCell[position].idPresensi)
            intent.putExtra("tanggalPresensi", itemCell[position].tanggal)
            context.startActivity(intent)
        }
    }
}