package com.sinarkelas.contract

interface DetailVerifikasiPembayaranContract {

    interface detailVerifikasiPembayaranView{
        fun getDetail(namaPengirim : String, createAt : String, statusTransfer : String)
        fun showDialogLoading()
        fun hideDialogLoading()
    }

    interface detailVerifikasiPembayaranPresenter{
        fun sendDetailVerifikasiPembayaran(idBilling : String)
    }
}