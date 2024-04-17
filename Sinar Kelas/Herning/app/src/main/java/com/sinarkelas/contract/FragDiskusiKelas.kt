package com.sinarkelas.contract

import com.sinarkelas.ui.dashboardhome.fragment.model.ListInformasiModel

interface FragDiskusiKelas {

    interface diskusiKelasView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun listAllInformasi(data : MutableList<ListInformasiModel> = mutableListOf())
        fun detailKelas(namaKelas : String, namaMataKuliah : String, deskripsiKelas : String, kodeKelas : String)
    }

    interface diskusiKelasPresenter{
        fun sendAllInformasi(iddosen: String, idKelas: String, idMahasiswa : String)
        fun sendDetailKelas(iddosen : String, idKelas : String)
    }
}