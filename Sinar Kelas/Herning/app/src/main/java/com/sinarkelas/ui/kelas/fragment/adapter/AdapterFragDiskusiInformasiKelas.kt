package com.sinarkelas.ui.kelas.fragment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.fragment.model.ListInformasiModel
import com.sinarkelas.ui.kelas.DetailDiskusiKelasActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.inflater_list_informasikelas.view.*
import java.text.SimpleDateFormat

class AdapterFragDiskusiInformasiKelas(private val itemCell : MutableList<ListInformasiModel>) : RecyclerView.Adapter<AdapterFragDiskusiInformasiKelas.InformasiKelasView>() {

    lateinit var context: Context

    class InformasiKelasView(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformasiKelasView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_informasikelas, parent, false)
        val ksv = InformasiKelasView(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: InformasiKelasView, position: Int) {
        var pattern = "HH:mm:ss, dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].createAt))

        holder.itemView.tvListNamaDosenInformasiKelas.text = itemCell[position].namaLengkap
        holder.itemView.tvListTanggalInformasiKelas.text = tanggalJoinFormat
        holder.itemView.tvListIsiInformasiKelas.text = itemCell[position].deskripsiinformasi
        holder.itemView.tvListJumlahKomentarInformasiKelas.text = itemCell[position].jumlahKomentar + " Komentar Balas"

        Picasso.get().load("https://sinarcenter.id/sinarkelasimage/"+itemCell[position].fotoProfile).into(holder.itemView.civFotoUserInformasiKelas)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, DetailDiskusiKelasActivity::class.java)
            intent.putExtra("idInformasi", itemCell[position].idInformasi)
            context.startActivity(intent)
        }
    }
}