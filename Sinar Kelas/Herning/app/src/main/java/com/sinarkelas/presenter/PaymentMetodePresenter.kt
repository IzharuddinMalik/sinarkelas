package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.PaymentMetodeContract
import com.sinarkelas.ui.upgradeakun.model.PaymentModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PaymentMetodePresenter(v : PaymentMetodeContract.paymentMetodeView) : PaymentMetodeContract.paymentMetodePresent {

    private var view : PaymentMetodeContract.paymentMetodeView? = v
    private var apiService = ApiClient.APIService()
    private var listPayment = mutableListOf<PaymentModel>()

    override fun sendPayment(idUser: String) {
        val request = apiService.getPaymentMetode(idUser)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<PaymentModel>>{
            override fun onFailure(call: Call<WrappedListResponse<PaymentModel>>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<PaymentModel>>,
                response: Response<WrappedListResponse<PaymentModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()
                        val body = response.body()
                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                val payment = body.info.get(i)

                                listPayment.add(PaymentModel("" + payment.idPay, "" + payment.namaPay,
                                    "" + payment.serverKey, "" + payment.clientKey,"" + payment.status))
                            }

                            view?.getPayment(listPayment)

                        } else{
                            view?.showToast(body.message)
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