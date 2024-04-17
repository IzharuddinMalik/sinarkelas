package com.sinarkelas.ui.kelas.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.kelas.model.ListKomentarDiskusiModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.inflater_list_komenter_kelas.view.*
import java.text.SimpleDateFormat

class AdapterKomentarDiskusi(private val itemCell : MutableList<ListKomentarDiskusiModel>) : RecyclerView.Adapter<AdapterKomentarDiskusi.KomentarViewHolder>() {

    lateinit var context: Context

    class KomentarViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomentarViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_komenter_kelas, parent, false)
        val ksv = KomentarViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: KomentarViewHolder, position: Int) {

        var pattern = "HH:mm:ss, dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].tanggal))

        holder.itemView.tvNamaUserListKomentar.text = itemCell[position].namaLengkap
        holder.itemView.tvTanggalListKomentar.text = itemCell[position].tanggal
        holder.itemView.tvIsiListKomentar.text = itemCell[position].komentar

        Picasso.get().load("https://sinarcenter.id/sinarkelasimage/"+itemCell[position].fotoProfile).into(holder.itemView.ivFotoUserListKomentar)
    }
}