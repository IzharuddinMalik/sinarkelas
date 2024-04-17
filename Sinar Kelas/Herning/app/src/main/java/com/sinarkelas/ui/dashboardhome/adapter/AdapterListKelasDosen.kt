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
import kotlinx.android.synthetic.main.inflater_list_kelas_dosen.view.*
import java.util.*

class AdapterListKelasDosen(private val itemCell : MutableList<ListKelasModel>) : RecyclerView.Adapter<AdapterListKelasDosen.ListKelasViewHolder>() {

    lateinit var context: Context

    class ListKelasViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListKelasViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_kelas_dosen, parent, false)
        val ksv = ListKelasViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun onBindViewHolder(holder: ListKelasViewHolder, position: Int) {
        holder.itemView.tvListNamaKelas.text = itemCell[position].namaKelas
        holder.itemView.tvListDeskripsiKelas.text = itemCell[position].deskripsiKelas
        holder.itemView.tvListNamaDosenKelas.text = itemCell[position].namaDosen

        holder.itemView.cvListKelasDosen.setCardBackgroundColor(randomColor())

        holder.itemView.cvListKelasDosen.setOnClickListener {
            var intent = Intent(context, DetailKelasActivity::class.java)
            var sharedPreferences : SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putString("idKelas", itemCell[position].idKelas)
            editor.commit()
            context.startActivity(intent)
        }
    }

    fun randomColor() : Int {
        var random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}