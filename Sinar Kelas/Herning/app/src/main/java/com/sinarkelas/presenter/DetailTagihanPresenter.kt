package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.DetailTagihanContract
import com.sinarkelas.ui.upgradeakun.model.DetailHistoriBillingModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DetailTagihanPresenter(v : DetailTagihanContract.detailTagihanView) : DetailTagihanContract.detailTagihanPresenter {

    private var apiService = ApiClient.APIService()
    private var view : DetailTagihanContract.detailTagihanView? = v

    override fun sendDetailTagihan(idBilling: String, idDosen: String) {
        val request = apiService.detailBilling(idBilling, idDosen)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<DetailHistoriBillingModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<DetailHistoriBillingModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<DetailHistoriBillingModel>>,
                response: Response<WrappedListResponse<DetailHistoriBillingModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()
                        val body = response.body()

                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                val obj = body.info.get(i)

                                view?.getDetailHistori("" + obj.idBilling, "" + obj.kodeBilling, "" + obj.idDosen,
                                "" + obj.namaDosen, "" + obj.namaPay, "" + obj.namaPaket, "" + obj.totalBill,
                                "" + obj.dateBill, "" + obj.overDueDate, "" + obj.statusBill )
                            }
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