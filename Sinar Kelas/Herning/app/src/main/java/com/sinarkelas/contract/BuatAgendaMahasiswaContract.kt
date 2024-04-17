package com.sinarkelas.contract

interface BuatAgendaMahasiswaContract {

    interface buatAgendaMahasiswaView{
        fun showToastAgenda(message : String)
        fun hideLoading()
        fun showLoading()
        fun successBuat()
    }

    interface buatAgendaMahasiswaPresenter{
        fun sendAgenda(judulAgenda : String, deskripsiAgenda : String, tanggalAgenda : String, jamAgenda : String, idMahasiswa : String)
    }
}