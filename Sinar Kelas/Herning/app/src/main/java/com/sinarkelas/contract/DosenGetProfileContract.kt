package com.sinarkelas.contract

interface DosenGetProfileContract {

    interface dosenProfileView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getProfileDosen(namaDosen : String, emailDosen : String, fotoProfileDosen : String, endDate : String, statusAkun : String)
    }

    interface dosenProfilePresenter{
        fun getProfile(idDosen : String)
    }
}