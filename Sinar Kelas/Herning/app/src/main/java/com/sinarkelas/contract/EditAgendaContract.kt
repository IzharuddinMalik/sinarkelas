package com.sinarkelas.contract

interface EditAgendaContract {

    interface editAgendaView{
        fun showLoadingEditAgenda()
        fun hideLoadingEditAgenda()
        fun showToastEditAgenda(message : String)
        fun successEditAgenda()
    }

    interface editAgendaPresenter{
        fun sendEditAgenda(idAgenda : String, judulAgenda : String, deskripsiAgenda : String, tanggalAgenda : String, jamAgenda : String, idDosen : String)
    }
}