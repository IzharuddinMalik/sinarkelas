package com.sinarkelas.contract

interface DetailMateriUmumContract {

    interface materiUmumView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun detailMateri(namaJudul : String, fileMateri : String, sumberMateri : String, tanggalMateri : String, namaAdmin : String)
    }

    interface materiUmumPresenter{
        fun getDetailMateriUmum(idUser : String, idMateriUmum : String)
    }
}