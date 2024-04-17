package com.sinarkelas.ui.kelas.fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.EditKelasContract
import com.sinarkelas.presenter.EditKelasPresenter
import com.sinarkelas.presenter.FragDiskusiKelasPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.ui.dashboardhome.DashboardHomeMahasiswa
import com.sinarkelas.ui.dashboardhome.fragment.model.ListInformasiModel
import com.sinarkelas.ui.kelas.BuatDiskusiKelasActivity
import com.sinarkelas.ui.kelas.EditKelasActivity
import com.sinarkelas.ui.kelas.fragment.adapter.AdapterFragDiskusiInformasiKelas
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.frag_diskusikelas.*

class FragDiskusiKelas : Fragment(), com.sinarkelas.contract.FragDiskusiKelas.diskusiKelasView, EditKelasContract.editKelasView {

    private var presenter : com.sinarkelas.contract.FragDiskusiKelas.diskusiKelasPresenter? = null
    private var stylingUtil : StylingUtil? = null
    private var presenterEditKelas : EditKelasContract.editKelasPresenter? = null

    var namaMataKuliahKirim : String? = ""
    var namaKelasKirim : String? = ""
    var deskripsiKelasKirim : String? = ""

    companion object{
        fun newInstance() : FragDiskusiKelas {
            val fragment = FragDiskusiKelas()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.frag_diskusikelas, container, false)
        presenter = FragDiskusiKelasPresenter(this)
        presenterEditKelas = EditKelasPresenter(this)
        stylingUtil = StylingUtil()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        cvBuatDiskusiFragKelas.setOnClickListener {
            startActivity(Intent(context, BuatDiskusiKelasActivity::class.java))
        }

        if (statusUser == "1"){
            presenter?.sendAllInformasi(idDosen!!,idKelas!!, "0")
            ivBackDetailKelas.setOnClickListener {
                startActivity(Intent(requireContext(), DashboardHomeDosen::class.java))
            }
            cvEditDetailKelas.visibility = View.VISIBLE
            cvBuatKuisDetailKelas.visibility = View.VISIBLE

            cvBuatKuisDetailKelas.setOnClickListener {
                dialogComingSoon()
            }

            cvEditDetailKelas.setOnClickListener {
                var intent = Intent(context, EditKelasActivity::class.java)
                intent.putExtra("namaMataKuliah", namaMataKuliahKirim)
                intent.putExtra("namaKelas", namaKelasKirim)
                intent.putExtra("deskripsiKelas", deskripsiKelasKirim)
                intent.putExtra("idKelas", idKelas)
                requireActivity().startActivity(intent)
            }
        } else{
            presenter?.sendAllInformasi(idDosen!!,idKelas!!, idMahasiswa!!)
            ivBackDetailKelas.setOnClickListener {
                startActivity(Intent(requireContext(), DashboardHomeMahasiswa::class.java))
            }

            cvEditDetailKelas.visibility = View.GONE
            cvBuatKuisDetailKelas.visibility = View.GONE
        }

        presenter?.sendDetailKelas(idDosen!!, idKelas!!)
    }

    override fun showToast(message: String) {

    }

    override fun showLoading() {
        svListSemuaInformasiKelas.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListSemuaInformasiKelas.hideShimmerAdapter()
    }

    override fun listAllInformasi(data: MutableList<ListInformasiModel>) {
        svListSemuaInformasiKelas.apply {
            svListSemuaInformasiKelas.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            svListSemuaInformasiKelas.adapter = AdapterFragDiskusiInformasiKelas(data)
        }
    }

    override fun detailKelas(namaKelas: String, namaMataKuliah: String, deskripsiKelas: String, kodeKelas : String) {
        tvDetailNamaKelas.text = namaKelas
        tvNamaMataKuliahDetailKelas.text = namaMataKuliah
        tvDeskripsiDetailKelas.text = deskripsiKelas
        tvKodeKelas.text = "Kode Kelas : " + kodeKelas

        namaMataKuliahKirim = namaMataKuliah
        namaKelasKirim = namaKelas
        deskripsiKelasKirim = deskripsiKelas

        stylingUtil?.montserratBoldTextview(context, tvDetailNamaKelas)
        stylingUtil?.montserratRegularTextview(context, tvKodeKelas)
        stylingUtil?.montserratRegularTextview(context, tvNamaMataKuliahDetailKelas)
        stylingUtil?.montserratRegularTextview(context, tvDeskripsiDetailKelas)

        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putString("kodeKelas", kodeKelas)
        editor.commit()
    }

    fun dialogComingSoon(){
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_comingsoon)

        stylingUtil = StylingUtil()

        var window : Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        dialog.show()
    }

    override fun showToastEditKelas(message: String) {

    }

    override fun showLoadingEditKelas() {

    }

    override fun hideLoadingEditKelas() {

    }

    override fun successEditKelas() {

    }
}