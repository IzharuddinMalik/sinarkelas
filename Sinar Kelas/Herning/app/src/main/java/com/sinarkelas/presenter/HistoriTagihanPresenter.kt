package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.HistoriTagihanContract
import com.sinarkelas.ui.upgradeakun.model.HistoriBillingModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HistoriTagihanPresenter(v : HistoriTagihanContract.historiTagihanView) : HistoriTagihanContract.historiTagihanPresenter {

    private var apiService = ApiClient.APIService()
    private var view : HistoriTagihanContract.historiTagihanView? = v
    private var listTagihan = mutableListOf<HistoriBillingModel>()

    override fun sendListHistori(idDosen: String) {
        val request = apiService.getHistoriBilling(idDosen)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<HistoriBillingModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<HistoriBillingModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<HistoriBillingModel>>,
                response: Response<WrappedListResponse<HistoriBillingModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()
                        val body = response.body()

                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                val obj = body.info.get(i)

                                listTagihan.add(HistoriBillingModel("" + obj.idBilling, "" + obj.kodeBilling, "" + obj.idDosen, "" + obj.namaDosen, "" + obj.namaPay,
                                    "" + obj.totalBill, "" + obj.dateBill, "" + obj.statusBill))
                            }

                            view?.getListHistori(listTagihan)
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