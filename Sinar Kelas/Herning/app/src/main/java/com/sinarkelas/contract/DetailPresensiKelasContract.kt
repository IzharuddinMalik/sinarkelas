package com.sinarkelas.contract

import com.sinarkelas.ui.kelas.model.ListDetailPresensiKelasModel

interface DetailPresensiKelasContract {

    interface detailPresensiKelasView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getListDetailPresensi(data : MutableList<ListDetailPresensiKelasModel> = mutableListOf())
        fun getJumlahHadir(jumlahHadir : String, jumlahIzin : String, jumlahAbsen : String)
    }

    interface detailPresensiKelasPresenter{
        fun sendDetailPresensi(idKelas : String, idDosen : String, idPresensiKelas : String)
        fun getJumlahHadirPresensi(idKelas: String, idDosen: String, idPresensiKelas: String)
    }
}