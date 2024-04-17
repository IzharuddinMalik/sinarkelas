package com.sinarkelas.contract

interface RegisterDosenContract {

    interface registerView{
        fun showLoading()
        fun hideLoading()
        fun showToast(message : String)
        fun successRegis(namaDosen : String)
    }

    interface registerPresenter{
        fun registerDosen(namaLengkap : String, email : String, password : String)
    }
}