package com.sinarkelas.contract

interface BayarBillingContract {

    interface bayarBillingView{
        fun showToastBayar(message : String)
        fun showLoadingBayar()
        fun hideLoadingBayar()
        fun successBayar(kodeReferensi : String)
    }

    interface bayarBillingPresenter{
        fun sendBayarBilling(idDosen : String, idPay : String, idPaket : String, totalBill : String)
    }
}