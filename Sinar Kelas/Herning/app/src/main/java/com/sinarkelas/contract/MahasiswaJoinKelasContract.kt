package com.sinarkelas.contract

interface MahasiswaJoinKelasContract {

    interface mhsJoinKelasView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun successJoin()
    }

    interface mhsJoinKelasPresenter{
        fun joinKelas(kodeKelas : String, idMahasiswa : String)
    }
}