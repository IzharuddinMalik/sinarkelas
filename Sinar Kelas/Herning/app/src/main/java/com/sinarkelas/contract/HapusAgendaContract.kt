package com.sinarkelas.contract

interface HapusAgendaContract {

    interface hapusAgendaView{
        fun showLoadingHapus()
        fun hideLoadingHapus()
        fun showToastHapus(message : String)
        fun successHapus()
    }

    interface hapusAgendaPresenter{
        fun sendHapusAgenda(idAgenda : String)
    }
}