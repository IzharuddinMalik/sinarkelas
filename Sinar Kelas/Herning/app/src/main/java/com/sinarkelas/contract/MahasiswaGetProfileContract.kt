package com.sinarkelas.contract

import kotlinx.android.parcel.Parcelize

interface MahasiswaGetProfileContract {

    interface mhsProfileView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getProfileMhs(namaMhs : String, emailMhs: String, fotoProfile : String)
    }

    interface mhsProfilePresenter{
        fun getProfile(idMahasiswa : String)
    }
}