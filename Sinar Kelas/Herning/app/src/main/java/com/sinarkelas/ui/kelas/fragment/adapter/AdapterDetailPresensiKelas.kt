package com.sinarkelas.ui.kelas.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.kelas.model.ListDetailPresensiKelasModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.inflater_list_detailpresensikelas.view.*

class AdapterDetailPresensiKelas(private val itemCell : MutableList<ListDetailPresensiKelasModel>) : RecyclerView.Adapter<AdapterDetailPresensiKelas.DetailPresensiKelasViewHolder>() {

    lateinit var context: Context

    class DetailPresensiKelasViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailPresensiKelasViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_detailpresensikelas, parent, false)
        val ksv = DetailPresensiKelasViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: DetailPresensiKelasViewHolder, position: Int) {

        holder.itemView.tvNamaMahasiswaDetailPresensiKelas.text = itemCell[position].namaMahasiswa
        if (itemCell[position].statusPresensi.equals("Belum Hadir")){
            holder.itemView.tvStatusPresensi.setTextColor(context.resources.getColor(R.color.grey_font))
        } else if (itemCell[position].statusPresensi.equals("Hadir")){
            holder.itemView.tvStatusPresensi.setTextColor(context.resources.getColor(R.color.greenColor))
        } else if (itemCell[position].statusPresensi.equals("Izin")){
            holder.itemView.tvStatusPresensi.setTextColor(context.resources.getColor(R.color.maroonRedColor))
        }
        holder.itemView.tvStatusPresensi.text = itemCell[position].statusPresensi

        Picasso.get().load("https://sinarcenter.id/sinarkelasimage/"+itemCell[position].fotoProfile).into(holder.itemView.civFotoMahasiswaDetailPresensiKelas)

    }
}