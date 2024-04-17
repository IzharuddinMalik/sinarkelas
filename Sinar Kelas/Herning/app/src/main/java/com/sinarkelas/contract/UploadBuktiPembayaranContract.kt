package com.sinarkelas.contract

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UploadBuktiPembayaranContract {

    interface uploadBuktiPembayaranView{
        fun showToast(message : String)
        fun hideLoading()
        fun showLoading()
        fun successUpload(namaPengirim: String, createAt : String)
    }

    interface uploadBuktiPembayaranPresenter{
        fun sendBuktiPembayaran(idBilling : String, idUser : String, namaPengirim : String, bankPengirim : String, tanggalTransfer : String, filteTransfer : String, statusTransfer : String)
    }

}