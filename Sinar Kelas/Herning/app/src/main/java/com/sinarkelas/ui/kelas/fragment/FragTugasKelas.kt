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
import com.sinarkelas.presenter.FragTugasKelasPresenter
import com.sinarkelas.ui.dashboardhome.fragment.model.ListTugasModel
import com.sinarkelas.ui.kelas.BuatTugasActivity
import com.sinarkelas.ui.kelas.fragment.adapter.AdapterFragTugasKelas
import kotlinx.android.synthetic.main.frag_tugaskelas.*

class FragTugasKelas : Fragment(), com.sinarkelas.contract.FragTugasKelas.tugasKelasView {

    private var presenter : com.sinarkelas.contract.FragTugasKelas.tugasPresenter? = null

    companion object{
        fun newInstance() : FragTugasKelas {
            val fragment = FragTugasKelas()
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
        var v = inflater.inflate(R.layout.frag_tugaskelas, container, false)
        presenter = FragTugasKelasPresenter(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences = context!!.getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        if (statusUser == "1"){
            presenter?.getTugasKelas(idDosen!!, idKelas!!, "0")

            fbTambahTugas.visibility = View.VISIBLE
            fbTambahTugas.setOnClickListener {
                startActivity(Intent(context, BuatTugasActivity::class.java))
            }
        } else{
            presenter?.getTugasKelas(idDosen!!, idKelas!!, idMahasiswa!!)

            fbTambahTugas.visibility = View.GONE
        }

    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        svListTugasKelas.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListTugasKelas.hideShimmerAdapter()
    }

    override fun listTugas(data: MutableList<ListTugasModel>) {
        svListTugasKelas.apply {
            svListTugasKelas.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            svListTugasKelas.adapter = AdapterFragTugasKelas(data)
        }
    }
}