package com.sinarkelas.contract

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface DosenBuatTugasContract {

    interface dosenBuatTugasView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun successBuat()
    }

    interface dosenBuatTugasPresenter{
        fun kirimTugas(judulTugas : RequestBody, deskripsiTugas : RequestBody, idDosen : RequestBody, idKelas : RequestBody, fileTugas : MultipartBody.Part, poinTugas : RequestBody, tenggatWaktu : RequestBody, statusHapusTugas : RequestBody)
    }
}