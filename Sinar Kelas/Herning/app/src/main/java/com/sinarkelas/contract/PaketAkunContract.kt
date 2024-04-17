package com.sinarkelas.contract

import com.sinarkelas.ui.upgradeakun.model.PaketAkunModel

interface PaketAkunContract {

    interface paketAkunView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getPaket(data : MutableList<PaketAkunModel> = mutableListOf())
    }

    interface paketAkunPresenter{
        fun sendPaket(idUser : String)
    }
}