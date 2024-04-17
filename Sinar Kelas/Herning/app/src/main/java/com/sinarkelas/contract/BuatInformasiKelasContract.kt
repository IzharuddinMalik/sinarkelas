package com.sinarkelas.contract

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface BuatInformasiKelasContract {

    interface buatInformasiView{
        fun showToast(message : String)
        fun hideLoading()
        fun showLoading()
        fun successBuat()
    }

    interface buatInformasiPresenter{
        fun sendBuatInformasi(deskripsiInformasi : RequestBody, idKelas : RequestBody, idDosen : RequestBody, idMahasiswa : RequestBody, fileInformasi : MultipartBody.Part, statusHapusInformasi : RequestBody)
    }
}