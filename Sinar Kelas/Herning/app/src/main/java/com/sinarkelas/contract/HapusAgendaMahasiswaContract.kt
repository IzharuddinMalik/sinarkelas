package com.sinarkelas.contract

interface HapusAgendaMahasiswaContract {

    interface hapusAgendaMahasiswaView{
        fun showToastHapusAgenda(message : String)
        fun showLoading()
        fun hideLoading()
        fun successHapus()
    }

    interface hapusAgendaMahasiswaPresenter{
        fun sendHapus(idAgenda : String)
    }
}