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
import com.sinarkelas.contract.FragAnggotaKelasContract
import com.sinarkelas.contract.KeluarKelasContract
import com.sinarkelas.presenter.FragAnggotaKelasPresenter
import com.sinarkelas.presenter.KeluarKelasPresenter
import com.sinarkelas.ui.dashboardhome.DashboardHomeDosen
import com.sinarkelas.ui.dashboardhome.DashboardHomeMahasiswa
import com.sinarkelas.ui.dashboardhome.fragment.model.ListAnggotaKelasModel
import com.sinarkelas.ui.kelas.fragment.adapter.AdapterFragAnggotaKelas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.frag_anggotakelas.*

class FragAnggotaKelas : Fragment(), FragAnggotaKelasContract.anggotaKelasView, KeluarKelasContract.keluarKelasView {

    private var presenter : FragAnggotaKelasContract.anggotaKelasPresenter? = null
    private var presenterKeluar : KeluarKelasContract.keluarKelasPresenter? = null
    var statusUserKeluar : String? = ""


    companion object{
        fun newInstance() : FragAnggotaKelas {
            val fragment = FragAnggotaKelas()
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
        var v = inflater.inflate(R.layout.frag_anggotakelas, container, false)
        presenter = FragAnggotaKelasPresenter(this)
        presenterKeluar = KeluarKelasPresenter(this)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        var idDosen : String? = sharedPreferences.getString("idDosen", "")
        var idKelas : String? = sharedPreferences.getString("idKelas", "")
        var statusUser : String? = sharedPreferences.getString("statusUser", "")
        var idMahasiswa : String? = sharedPreferences.getString("idMahasiswa", "")

        statusUserKeluar = statusUser

        if (statusUser == "1"){
            presenter?.getAnggotaKelas(idDosen!!, idKelas!!, "0")
        } else{
            presenter?.getAnggotaKelas(idDosen!!, idKelas!!, idMahasiswa!!)
        }

        cvKeluarKelasDetailKelas.setOnClickListener {
            if (statusUser == "1"){
                presenterKeluar?.sendKeluarKelas(idDosen!!, "0", idKelas!!)
            } else{
                presenterKeluar?.sendKeluarKelas(idDosen!!, idMahasiswa!!, idKelas!!)
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        svListTemanDetailKelas.showShimmerAdapter()
    }

    override fun hideLoading() {
        svListTemanDetailKelas.hideShimmerAdapter()
    }

    override fun getListAnggotaKelas(data: MutableList<ListAnggotaKelasModel>) {
        svListTemanDetailKelas.apply {
            svListTemanDetailKelas.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            svListTemanDetailKelas.adapter = AdapterFragAnggotaKelas(data)
        }
    }

    override fun getNamaDosen(namaDosen: String, fotoProfile : String) {
        tvDetailDosenKelas.text = namaDosen
        Picasso.get().load("https://sinarcenter.id/sinarkelasimage/" + fotoProfile).into(ivFotoProfileDosenAnggota)
    }

    override fun showToastKeluarKelas(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoadingKeluarKelas() {

    }

    override fun hideLoadingKeluarKelas() {

    }

    override fun successKeluar() {
        if (statusUserKeluar == "1"){
            startActivity(Intent(context, DashboardHomeDosen::class.java)).apply {
                requireActivity().finish()
            }
        } else{
            startActivity(Intent(context, DashboardHomeMahasiswa::class.java)).apply {
                requireActivity().finish()
            }
        }
    }
}