package com.sinarkelas.contract

interface LoginDosenContract {

    interface loginView{
        fun showLoading()
        fun hideLoading()
        fun showToast(message : String)
        fun successLogin(idDosen : String, statusUser : String, statusAkun : String, tipeAkun : String)
    }

    interface loginPresenter{
        fun loginDosen(email : String, password : String, token : String)
    }
}