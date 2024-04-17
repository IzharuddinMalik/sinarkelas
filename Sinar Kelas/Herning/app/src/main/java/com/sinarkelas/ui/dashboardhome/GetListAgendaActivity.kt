package com.sinarkelas.ui.dashboardhome

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sinarkelas.R
import com.sinarkelas.contract.ListAgendaContract
import com.sinarkelas.presenter.ListAgendaPresenter
import com.sinarkelas.ui.dashboardhome.adapter.AgendaListAdapter
import com.sinarkelas.ui.dashboardhome.fragment.model.AgendaModel
import kotlinx.android.synthetic.main.activity_get_list_agenda.*

class GetListAgendaActivity : AppCompatActivity(), ListAgendaContract.listAgendaView {

    private var presenterListAgenda : ListAgendaContract.listAgendaPresenter? = null
    var idDosen : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_list_agenda)

        presenterListAgenda = ListAgendaPresenter(this)

        var sharedPreferences : SharedPreferences = this.getSharedPreferences("session", Context.MODE_PRIVATE)
        idDosen = sharedPreferences.getString("idDosen", "")

        presenterListAgenda?.sendListAgenda(idDosen!!)

        ivBackListAgenda.setOnClickListener {
            startActivity(Intent(this, DashboardHomeDosen::class.java)).apply {
                finish()
            }
        }
    }

    override fun showLoadingListAgenda() {
        svListAgenda.showShimmerAdapter()
    }

    override fun hideLoadingListAgenda() {
        svListAgenda.hideShimmerAdapter()
    }

    override fun showToastListAgenda(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun getListAgenda(data: MutableList<AgendaModel>) {
        svListAgenda.apply {
            svListAgenda.layoutManager = LinearLayoutManager(this@GetListAgendaActivity)
            svListAgenda.adapter = AgendaListAdapter(data)
        }
    }
}