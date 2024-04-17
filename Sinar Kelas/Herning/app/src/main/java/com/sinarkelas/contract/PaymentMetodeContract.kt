package com.sinarkelas.contract

import com.sinarkelas.ui.upgradeakun.model.PaymentModel

interface PaymentMetodeContract {

    interface paymentMetodeView{
        fun showToast(message : String)
        fun hideLoading()
        fun showLoading()
        fun getPayment(data : MutableList<PaymentModel> = mutableListOf())
    }

    interface paymentMetodePresent{
        fun sendPayment(idUser : String)
    }
}