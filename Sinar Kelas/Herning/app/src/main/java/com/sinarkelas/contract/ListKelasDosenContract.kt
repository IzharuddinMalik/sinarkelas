package com.sinarkelas.contract

import com.sinarkelas.ui.dashboardhome.fragment.model.ListKelasModel

interface ListKelasDosenContract {

    interface kelasView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun listKelas(data : MutableList<ListKelasModel> = mutableListOf())
    }

    interface kelasPresenter{
        fun getListKelas(iddosen : String, idMahasiswa : String, statusUser : String)
    }
}