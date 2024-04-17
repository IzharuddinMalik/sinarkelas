package com.sinarkelas.contract

interface EditAgendaMahasiswaContract {

    interface editAgendaMahasiswaView{
        fun showToastEdit(message : String)
        fun showLoading()
        fun hideLoading()
        fun successEdit()
    }

    interface editAgendaMahasiswaPresenter{
        fun sendEditAgenda(idAgenda : String, judulAgenda : String, deskripsiAgenda : String, tanggalAgenda : String, jamAgenda : String, idMahasiswa : String)
    }
}