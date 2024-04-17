package com.sinarkelas.contract

interface KeluarKelasContract {

    interface keluarKelasView{
        fun showToastKeluarKelas(message : String)
        fun showLoadingKeluarKelas()
        fun hideLoadingKeluarKelas()
        fun successKeluar()
    }

    interface keluarKelasPresenter{
        fun sendKeluarKelas(idDosen : String, idMahasiswa : String, idKelas : String)
    }
}