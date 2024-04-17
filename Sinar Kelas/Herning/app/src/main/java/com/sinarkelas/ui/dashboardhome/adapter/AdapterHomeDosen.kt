package com.sinarkelas.ui.dashboardhome.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.dashboardhome.DetailMateriUmumActivity
import com.sinarkelas.ui.dashboardhome.MateriUmumModel
import kotlinx.android.synthetic.main.inflater_list_materi_umum.view.*

class AdapterHomeDosen(private val itemCell : MutableList<MateriUmumModel>) : RecyclerView.Adapter<AdapterHomeDosen.HomeViewHolder>() {

    lateinit var context: Context

    class HomeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_materi_umum, parent, false)
        val ksv = HomeViewHolder(view)
        context = parent.context
        return ksv
    }

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.itemView.tvListJudulMateriHome.text = itemCell[position].judulMateriUmum

        holder.itemView.setOnClickListener {
            var intent = Intent(context, DetailMateriUmumActivity::class.java)
            intent.putExtra("idmateriumum", itemCell[position].idMateriUmum)
            context.startActivity(intent)
        }
    }
}