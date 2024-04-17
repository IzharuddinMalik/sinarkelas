package com.sinarkelas.contract

import com.sinarkelas.ui.dashboardhome.fragment.model.ListAnggotaKelasModel

interface FragAnggotaKelasContract {

    interface anggotaKelasView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getListAnggotaKelas(data : MutableList<ListAnggotaKelasModel> = mutableListOf())
        fun getNamaDosen(namaDosen : String, fotoProfile : String)
    }

    interface anggotaKelasPresenter{
        fun getAnggotaKelas(idDosen : String, idKelas : String, idMahasiswa : String)
    }
}