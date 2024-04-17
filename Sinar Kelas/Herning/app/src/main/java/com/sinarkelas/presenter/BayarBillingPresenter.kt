package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.billingResponse
import com.sinarkelas.contract.BayarBillingContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BayarBillingPresenter(v : BayarBillingContract.bayarBillingView) : BayarBillingContract.bayarBillingPresenter {

    private var view : BayarBillingContract.bayarBillingView? = v
    private var apiService = ApiClient.APIService()

    override fun sendBayarBilling(
        idDosen: String,
        idPay: String,
        idPaket: String,
        totalBill: String
    ) {
        val request = apiService.bayarBilling(idDosen, idPay, idPaket, totalBill)
        view?.hideLoadingBayar()
        request.enqueue(object : Callback<billingResponse>{
            override fun onFailure(call: Call<billingResponse>, t: Throwable) {
                view?.hideLoadingBayar()
                view?.showToastBayar(t.message!!)
            }

            override fun onResponse(
                call: Call<billingResponse>,
                response: Response<billingResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")){
                            view?.successBayar(body.kodeTrans)
                            view?.hideLoadingBayar()
                        } else{
                            view?.showToastBayar(body.message)
                            view?.hideLoadingBayar()
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                    } catch (e : IOException){
                        e.printStackTrace()
                    }
                }
            }
        })
    }

}