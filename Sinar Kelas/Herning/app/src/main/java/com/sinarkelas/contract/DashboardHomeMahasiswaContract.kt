package com.sinarkelas.contract

import com.sinarkelas.ui.dashboardhome.MateriUmumModel

interface DashboardHomeMahasiswaContract {

    interface dashboardView{
        fun showLoading()
        fun hideLoading()
        fun showToast(message : String)
        fun getHeaderMahasiswa(namaMahasiswa : String, emailMahasiswa : String, password : String, fotoProfile : String)
        fun getJumlahKelas(jumlahKelas : String)
        fun getJumlahTugas(jumlahTugas : String)
        fun getJumlahAgenda(jumlahAgenda : String)
    }

    interface dashboardPresenter{
        fun sendDashboard(idMahasiswa : String)
    }
}