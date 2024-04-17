package com.sinarkelas.contract

interface EditKelasContract {

    interface editKelasView{
        fun showToastEditKelas(message : String)
        fun showLoadingEditKelas()
        fun hideLoadingEditKelas()
        fun successEditKelas()
    }

    interface editKelasPresenter{
        fun sendEditKelas(idKelas : String, namaMataKuliah : String, namaKelas : String, deskripsiKelas : String, idDosen : String)
    }
}