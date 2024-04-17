package com.sinarkelas.ui.kelas.fragment.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sinarkelas.R
import com.sinarkelas.ui.kelas.ShowPDFActivity
import com.sinarkelas.ui.kelas.model.ListMahasiswaKumpulTugasModel
import com.sinarkelas.util.StylingUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.inflater_list_mahasiswa_uploadtugas.view.*
import java.text.SimpleDateFormat

class AdapterMahasiswaUploadTugas(private val itemCell : MutableList<ListMahasiswaKumpulTugasModel>) : RecyclerView.Adapter<AdapterMahasiswaUploadTugas.UploadTugasViewHolder>() {

    lateinit var context: Context
    var lampiranFile : String? = ""
    var stylingUtil : StylingUtil? = null

    class UploadTugasViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int {
        return itemCell.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadTugasViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.inflater_list_mahasiswa_uploadtugas, parent, false)
        val ksv = UploadTugasViewHolder(view)
        context = parent.context
        stylingUtil = StylingUtil()
        return ksv
    }

    override fun onBindViewHolder(holder: UploadTugasViewHolder, position: Int) {
        var pattern = "HH:mm:ss, dd MMMM yyyy"
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat(pattern)
        var tanggalFromAPI = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var tanggalJoinFormat = simpleDateFormat.format(tanggalFromAPI.parse(itemCell[position].tanggal))

        holder.itemView.tvListNamaFileUploadTugas.text = itemCell[position].fileTugas
        holder.itemView.tvNamaMahasiswaDetailTugas.text = itemCell[position].namaMahasiswa
        holder.itemView.tvListTanggalFileUploadTugas.text = tanggalJoinFormat

        lampiranFile = itemCell[position].fileTugas

        Picasso.get().load("https://sinarcenter.id/sinarkelasimage/"+itemCell[position].fotoProfile).into(holder.itemView.civFotoMahasiswaDetailTugas)

        holder.itemView.tvListNamaFileUploadTugas.setOnClickListener {
            bacaFile()
//            var intent = Intent(context, ShowPDFActivity::class.java)
//            intent.putExtra("pdfFile", lampiranFile)
//            context.startActivity(intent)
        }

        stylingUtil?.montserratBoldTextview(context, holder.itemView.tvNamaMahasiswaDetailTugas)
        stylingUtil?.montserratRegularTextview(context, holder.itemView.tvListNamaFileUploadTugas)
    }

    fun bacaFile() {
        try {
            var uri = Uri.parse("https://sinarcenter.id/fileuploadapps/" + lampiranFile)

            if (lampiranFile.toString().contains(".pdf")) {
                var intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(uri, "application/pdf")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else if (lampiranFile.toString().contains(".docx")) {
                var intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://perumku.com/upload/file/" + lampiranFile)
                )
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else if (lampiranFile.toString().contains(".pptx")) {
                var intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://perumku.com/upload/file/" + lampiranFile)
                )
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else if (lampiranFile.toString().contains(".xlsx")) {
                var intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://perumku.com/upload/file/" + lampiranFile)
                )
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else if (lampiranFile.toString().contains(".doc")) {
                var intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://perumku.com/upload/file/" + lampiranFile)
                )
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else if (lampiranFile.toString().contains(".ppt")) {
                var intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://perumku.com/upload/file/" + lampiranFile)
                )
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else if (lampiranFile.toString().contains(".xls")) {
                var intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://view.officeapps.live.com/op/view.aspx?src=" + "http://perumku.com/upload/file/" + lampiranFile)
                )
                intent.setData(uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}