package com.sinarkelas.contract

interface BuatAgendaContract {

    interface buatAgendaView{
        fun showLoadingAgenda()
        fun hideLoadingAgenda()
        fun showToastAgenda(message : String)
        fun successAgenda()
    }

    interface buatAgendaPresenter{
        fun buatAgenda(judulAgenda : String, deskripsiAgenda : String, tanggalAgenda : String, jamAgenda : String, idDosen : String)
    }
}