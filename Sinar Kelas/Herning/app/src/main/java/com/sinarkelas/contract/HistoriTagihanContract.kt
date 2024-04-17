package com.sinarkelas.contract

import com.sinarkelas.ui.upgradeakun.model.HistoriBillingModel

interface HistoriTagihanContract {

    interface historiTagihanView{
        fun showToast(message : String)
        fun hideLoading()
        fun showLoading()
        fun getListHistori(data : MutableList<HistoriBillingModel> = mutableListOf())
    }

    interface historiTagihanPresenter{
        fun sendListHistori(idDosen : String)
    }
}