package com.sinarkelas.contract

interface LoginMahasiswaContract {

    interface loginView{
        fun showLoading()
        fun hideLoading()
        fun showToast(message : String)
        fun successLogin(idMahasiswa : String, statusUser : String)
    }

    interface loginPresenter{
        fun loginMahasiswa(email : String, password : String, token : String)
    }
}