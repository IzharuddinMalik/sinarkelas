package com.sinarkelas.contract

interface CekEmailMahasiswaContract {

    interface cekEmailMahasiswaView{
        fun showToastCekEmail(message : String)
        fun showLoadingCekEmail()
        fun hideLoadingCekEmail()
        fun successCekEmail(success : String)
    }

    interface cekEmailMahasiswaPresenter{
        fun sendCekEmail(email : String)
    }
}