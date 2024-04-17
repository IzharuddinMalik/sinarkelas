package com.sinarkelas.contract

import com.sinarkelas.ui.kelas.model.ListPresensiKelasModel

interface FragPresensiKelasContract {

    interface presensiKelasView{
        fun showToast(message : String)
        fun hideLoading()
        fun showLoading()
        fun getList(data : MutableList<ListPresensiKelasModel> = mutableListOf())
    }

    interface presensiKelasPresenter{
        fun sendGetList(idDosen : String, idKelas : String)
    }
}