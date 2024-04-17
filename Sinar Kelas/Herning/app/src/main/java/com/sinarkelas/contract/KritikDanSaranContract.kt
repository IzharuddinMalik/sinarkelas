package com.sinarkelas.contract

interface KritikDanSaranContract {

    interface kritiksaranView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun successKirim()
    }

    interface kritiksaranPresenter{
        fun kirimKritikSaran(kritik : String, saran : String, namaPengirim : String, emailPengirim : String)
    }
}