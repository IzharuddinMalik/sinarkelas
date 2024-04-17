package com.sinarkelas.contract

import com.sinarkelas.ui.dashboardhome.fragment.model.ListTugasModel

interface FragTugasKelas {

    interface tugasKelasView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun listTugas(data : MutableList<ListTugasModel> = mutableListOf())
    }

    interface tugasPresenter{
        fun getTugasKelas(idDosen : String, idKelas : String, idMahasiswa : String)
    }
}