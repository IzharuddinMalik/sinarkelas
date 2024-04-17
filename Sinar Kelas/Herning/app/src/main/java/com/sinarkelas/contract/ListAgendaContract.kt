package com.sinarkelas.contract

import com.sinarkelas.ui.dashboardhome.fragment.model.AgendaModel

interface ListAgendaContract {

    interface listAgendaView{
        fun showLoadingListAgenda()
        fun hideLoadingListAgenda()
        fun showToastListAgenda(message : String)
        fun getListAgenda(data : MutableList<AgendaModel> = mutableListOf())
    }

    interface listAgendaPresenter{
        fun sendListAgenda(idDosen : String)
    }
}