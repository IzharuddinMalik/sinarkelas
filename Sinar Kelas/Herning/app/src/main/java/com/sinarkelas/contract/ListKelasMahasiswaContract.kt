package com.sinarkelas.contract

import com.sinarkelas.ui.dashboardhome.fragment.model.ListKelasModel

interface ListKelasMahasiswaContract {

    interface listKelasMhsView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getListKelas(data : MutableList<ListKelasModel> = mutableListOf())
    }

    interface listKelasMhsPresenter{
        fun getListKelasMhs(iddosen : String, idMahasiswa : String, statusUser : String)
    }
}