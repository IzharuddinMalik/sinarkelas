package com.sinarkelas.contract

interface MahasiswaEditProfileContract {

    interface mhsEditProfileView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun successEdit()
    }

    interface mhsEditProfilePresenter{
        fun editProfile(idMahasiswa : String, namaMahasiswa : String, emailMahasiswa : String, passMahasiswa : String, fotoProfile : String)
    }
}