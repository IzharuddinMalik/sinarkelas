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
import com.sinarkelas.contract.ListKelasMahasiswaContract
import com.sinarkelas.presenter.ListKelasMahasiswaPresenter
import com.sinarkelas.ui.dashboardhome.adapter.AdapterListKelasMhs
import com.sinarkelas.ui.dashboardhome.fragment.model.ListKelasModel
import com.sinarkelas.ui.kelas.MahasiswaJoinKelasActivity
import kotlinx.android.synthetic.main.frag_kelas_mahasiswa.*

class FragKelasMahasiswa : Fragment(), ListKelasMahasiswaContract.listKelasMhsView {

    var presenter : ListKelasMahasiswaContract.listKelasMhsPresenter? = null

    companion object{
        fun newInstance() : FragKelasMahasiswa {
            val fragment = FragKelasMahasiswa()
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
        var v = inflater.inflate(R.layout.frag_kelas_mahasiswa, container, false)
        presenter = ListKelasMahasiswaPresenter(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences = context!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")

        presenter?.getListKelasMhs("0", idMahasiswa!!, statusUser!!)

        fbJoinKelasMhs.setOnClickListener {
            startActivity(Intent(context, MahasiswaJoinKelasActivity::class.java))
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun getListKelas(data: MutableList<ListKelasModel>) {
        svListKelasMahasiswa.apply {
            svListKelasMahasiswa.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            svListKelasMahasiswa.adapter = AdapterListKelasMhs(data)
        }
    }
}