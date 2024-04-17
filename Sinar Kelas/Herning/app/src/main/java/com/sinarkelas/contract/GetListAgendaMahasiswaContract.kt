package com.sinarkelas.contract

import com.sinarkelas.ui.dashboardhome.fragment.model.AgendaModel

interface GetListAgendaMahasiswaContract {

    interface getListAgendaMahasiswaView{
        fun showToastListAgenda(message : String)
        fun showLoading()
        fun hideLoading()
        fun getListAgenda(data : MutableList<AgendaModel> = mutableListOf())
    }

    interface getListAgendaMahasiswaPresenter{
        fun sendListAgenda(idMahasiswa : String)
    }
}