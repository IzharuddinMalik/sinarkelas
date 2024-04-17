package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.HapusAgendaContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HapusAgendaPresenter(v : HapusAgendaContract.hapusAgendaView) : HapusAgendaContract.hapusAgendaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : HapusAgendaContract.hapusAgendaView? = v

    override fun sendHapusAgenda(idAgenda: String) {
        val request = apiService.hapusAgenda(idAgenda)
        view?.showLoadingHapus()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoadingHapus()
                view?.showToastHapus(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoadingHapus()
                            view?.successHapus()
                        } else{
                            view?.showToastHapus(body.message)
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