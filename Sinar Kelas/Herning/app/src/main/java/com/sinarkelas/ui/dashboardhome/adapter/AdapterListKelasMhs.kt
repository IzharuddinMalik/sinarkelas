package com.sinarkelas.ui.dashboardhome.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.fragment.model.ListKelasModel
import com.sinarkelas.ui.kelas.DetailKelasActivity
import kotlinx.android.synthetic.main.inflater_list_kelas_mahasiswa.view.*
import java.util.*

class AdapterListKelasMhs(private val itemCell : MutableList<ListKelasModel>) : RecyclerView.Adapter<AdapterListKelasMhs.KelasMahasiswaViewHolder>() {

    lateinit var context: Context

    class KelasMahasiswaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KelasMahasiswaViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_kelas_mahasiswa, parent, false)
        val ksv = KelasMahasiswaViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: KelasMahasiswaViewHolder, position: Int) {

        holder.itemView.tvListNamaKelasMhs.text = itemCell[position].namaKelas
        holder.itemView.tvListDeskripsiKelasMhs.text = itemCell[position].deskripsiKelas
        holder.itemView.tvListNamaDosenKelasMhs.text = itemCell[position].namaDosen

        holder.itemView.cvListKelasMahasiswa.setCardBackgroundColor(randomColor())

        holder.itemView.cvListKelasMahasiswa.setOnClickListener {
            var intent = Intent(context, DetailKelasActivity::class.java)
            var sharedPreferences : SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putString("idKelas", itemCell[position].idKelas)
            editor.putString("idDosen", itemCell[position].idDosen)
            editor.commit()
            context.startActivity(intent)
        }
    }

    fun randomColor() : Int {
        var random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}