package com.sinarkelas.ui.dashboardhome

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.GetListAgendaMahasiswaContract
import com.sinarkelas.presenter.GetListAgendaMahasiswaPresenter
import com.sinarkelas.ui.dashboardhome.adapter.AdapterAgendaMahasiswa
import com.sinarkelas.ui.dashboardhome.fragment.model.AgendaModel
import kotlinx.android.synthetic.main.activity_get_list_agenda_mahasiswa.*

class GetListAgendaMahasiswaActivity : AppCompatActivity(), GetListAgendaMahasiswaContract.getListAgendaMahasiswaView {

    private var presenterListAgenda : GetListAgendaMahasiswaContract.getListAgendaMahasiswaPresenter? = null
    private var idMahasiswa : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_list_agenda_mahasiswa)

        var sharedPreferences : SharedPreferences = this.getSharedPreferences("session", Context.MODE_PRIVATE)
        idMahasiswa = sharedPreferences.getString("idMahasiswa", "")

        presenterListAgenda = GetListAgendaMahasiswaPresenter(this)
        presenterListAgenda?.sendListAgenda(idMahasiswa!!)
    }

    override fun showToastListAgenda(message: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun getListAgenda(data: MutableList<AgendaModel>) {
        svListAgendaMahasiswa.apply {
            svListAgendaMahasiswa.layoutManager = LinearLayoutManager(this@GetListAgendaMahasiswaActivity)
            svListAgendaMahasiswa.adapter = AdapterAgendaMahasiswa(data)
        }
    }
}