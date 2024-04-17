package com.sinarkelas.contract

interface CekEmailDosenContract {

    interface cekEmailDosenView{
        fun showLoadingCekEmail()
        fun hideLoadingCekEmail()
        fun showToastCekEmail(message : String)
        fun success(success : String)
    }

    interface cekEmailDosenPresenter{
        fun sendCekEmail(email : String)
    }
}