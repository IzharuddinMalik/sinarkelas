package com.sinarkelas.ui.dashboardhome.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.ListKelasDosenContract
import com.sinarkelas.presenter.ListKelasDosenPresenter
import com.sinarkelas.ui.buatkelas.BuatKelasActivity
import com.sinarkelas.ui.dashboardhome.adapter.AdapterListKelasDosen
import com.sinarkelas.ui.dashboardhome.fragment.model.ListKelasModel
import com.sinarkelas.util.StylingUtil
import kotlinx.android.synthetic.main.frag_kelas_dosen.*

class FragKelasDosen : Fragment(), ListKelasDosenContract.kelasView {

    private var presenter : ListKelasDosenContract.kelasPresenter? = null
    private var stylingUtil : StylingUtil? = null

    companion object{
        fun newInstance() : FragKelasDosen {
            val fragment = FragKelasDosen()
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
        var v = inflater.inflate(R.layout.frag_kelas_dosen, container, false)

        presenter = ListKelasDosenPresenter(this)
        stylingUtil = StylingUtil()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var statusAkun : String? = sharedPreferences.getString("statusAkun", "")

        if (statusAkun.equals("EXPIRED")){
            framekelasdosen.visibility = View.GONE
            infoExpired.visibility = View.VISIBLE

            stylingUtil?.montserratBoldTextview(requireContext(), tvJudulInfoExpiredAkun)
        } else{
            framekelasdosen.visibility = View.VISIBLE
            infoExpired.visibility = View.GONE
        }

        presenter?.getListKelas(idDosen!!, "0", statusUser!!)

        fbTambahKelasDosen.setOnClickListener {
            tambahKelas()
        }
    }

    override fun showToast(message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        svListKelasDosen.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListKelasDosen.hideShimmerAdapter()
    }

    override fun listKelas(data: MutableList<ListKelasModel>) {

        svListKelasDosen.apply {
            svListKelasDosen.layoutManager = LinearLayoutManager(requireContext())
            svListKelasDosen.adapter = AdapterListKelasDosen(data)
        }
    }

    fun tambahKelas(){
        startActivity(Intent(context, BuatKelasActivity::class.java))
    }
}