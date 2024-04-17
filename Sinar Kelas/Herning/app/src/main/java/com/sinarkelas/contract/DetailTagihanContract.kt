package com.sinarkelas.contract

interface DetailTagihanContract {

    interface detailTagihanView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun getDetailHistori(idBilling : String, kodeBilling : String, idDosen : String, namaDosen : String, namaPay : String, namaPaket : String,
        totalBill : String, dateBill : String, overdueDate : String, statusBill : String)
    }

    interface detailTagihanPresenter{
        fun sendDetailTagihan(idBilling: String, idDosen: String)
    }
}