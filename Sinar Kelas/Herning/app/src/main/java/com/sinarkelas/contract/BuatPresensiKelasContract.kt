package com.sinarkelas.contract

interface BuatPresensiKelasContract {

    interface buatPresensiView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface buatPresensiPresenter{
        fun buatPresensi(idKelas : String, idDosen : String)
    }
}