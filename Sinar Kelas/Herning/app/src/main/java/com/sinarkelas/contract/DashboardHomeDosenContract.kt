package com.sinarkelas.contract

interface DashboardHomeDosenContract {

    interface dashboardView{
        fun showLoading()
        fun hideLoading()
        fun showToast(message : String)
        fun dosenProfile(namaDosen : String, fotoProfile : String, jenjangPengajar : String)
        fun jumlahKelas(jumlahKelasDosen : String)
        fun jumlahSiswa(jumlahSiswaDosen : String)
        fun jumlahTugas(jumlahTugasDosen : String)
        fun jumlahAgenda(jumlahAgenda : String)
    }

    interface dashboardPresenter{
        fun getInfoDashboard(idDosen : String)
    }
}