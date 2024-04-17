package com.sinarkelas.contract

interface DosenEditProfileContract {

    interface dosenEditView{
        fun showToast(message : String)
        fun hideLoading()
        fun showLoading()
        fun successEdit()
    }

    interface dosenEditPresenter{
        fun editProfile(iddosen : String, namaDosen : String, emailDosen : String, passDosen : String, fotoProfile : String, jenjangPengajar : String)
    }
}