package com.sinarkelas.contract

import com.sinarkelas.ui.notifikasi.model.NotifikasiModel

interface GetNotifikasiContract {

    interface getNotifView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getListNotif(data : MutableList<NotifikasiModel> = mutableListOf())
    }

    interface getNotifPresenter{
        fun sendGetNotif(idUser : String)
    }

}