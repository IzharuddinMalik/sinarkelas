package com.sinarkelas.contract

interface RegisterMahasiswa {

    interface registerMhsView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun success(namaMahasiswa : String)
    }

    interface registerMhsPresenter{
        fun regisMhs(namaLengkap : String, email : String, password : String)
    }
}