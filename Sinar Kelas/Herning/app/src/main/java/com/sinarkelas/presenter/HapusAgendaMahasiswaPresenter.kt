package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.HapusAgendaMahasiswaContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HapusAgendaMahasiswaPresenter(v : HapusAgendaMahasiswaContract.hapusAgendaMahasiswaView) : HapusAgendaMahasiswaContract.hapusAgendaMahasiswaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : HapusAgendaMahasiswaContract.hapusAgendaMahasiswaView? = v

    override fun sendHapus(idAgenda: String) {
        val request = apiService.hapusAgendaMahasiswa(idAgenda)
        view?.showLoading()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoading()
                view?.showToastHapusAgenda(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()

                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.successHapus()
                        } else{
                            view?.showToastHapusAgenda(body.message)
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