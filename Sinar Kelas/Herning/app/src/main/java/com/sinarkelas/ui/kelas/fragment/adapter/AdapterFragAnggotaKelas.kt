package com.sinarkelas.ui.kelas.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.fragment.model.ListAnggotaKelasModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.inflater_list_anggotakelas.view.*

class AdapterFragAnggotaKelas(private val itemCell : MutableList<ListAnggotaKelasModel>) : RecyclerView.Adapter<AdapterFragAnggotaKelas.AnggotaViewHolder>() {

    lateinit var context: Context

    class AnggotaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnggotaViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_anggotakelas, parent, false)
        val ksv = AnggotaViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: AnggotaViewHolder, position: Int) {

        holder.itemView.tvNamaMahasiswaAnggotaKelas.text = itemCell[position].namaMahasiswa

        Picasso.get().load("https://sinarcenter.id/sinarkelasimage/" + itemCell[position].fotoProfile).into(holder.itemView.civFotoProfileAnggotaKelas)

    }
}