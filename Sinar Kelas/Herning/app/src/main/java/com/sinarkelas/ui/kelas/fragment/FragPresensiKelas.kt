package com.sinarkelas.ui.kelas.fragment

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
import com.sinarkelas.contract.FragPresensiKelasContract
import com.sinarkelas.presenter.FragPresensiKelasPresenter
import com.sinarkelas.ui.kelas.BuatPresensiKelasActivity
import com.sinarkelas.ui.kelas.fragment.adapter.AdapterFragPresensiKelas
import com.sinarkelas.ui.kelas.model.ListPresensiKelasModel
import kotlinx.android.synthetic.main.frag_presensikelas.*

class FragPresensiKelas : Fragment(), FragPresensiKelasContract.presensiKelasView {

    private var presenter : FragPresensiKelasContract.presensiKelasPresenter? = null

    companion object{
        fun newInstance() : FragPresensiKelas {
            val fragment = FragPresensiKelas()
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
        var v = inflater.inflate(R.layout.frag_presensikelas, container, false)
        presenter = FragPresensiKelasPresenter(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences = context!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        presenter?.sendGetList(idDosen!!, idKelas!!)

        if(statusUser == "1"){
            fbTambahPresensiKelas.visibility = View.VISIBLE
            fbTambahPresensiKelas.setOnClickListener {
                startActivity(Intent(context, BuatPresensiKelasActivity::class.java))
            }
        } else{
            fbTambahPresensiKelas.visibility = View.GONE
        }

    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        svListPresensiKelas.hideShimmerAdapter()
    }

    override fun showLoading() {
        svListPresensiKelas.showShimmerAdapter()
    }

    override fun getList(data: MutableList<ListPresensiKelasModel>) {
        svListPresensiKelas.apply {
            svListPresensiKelas.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            svListPresensiKelas.adapter = AdapterFragPresensiKelas(data)
        }
    }
}