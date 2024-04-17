package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.KritikDanSaranContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class KritikDanSaranPresenter(v : KritikDanSaranContract.kritiksaranView) : KritikDanSaranContract.kritiksaranPresenter {

    var apiService = ApiClient.APIService()
    var view : KritikDanSaranContract.kritiksaranView? = v

    override fun kirimKritikSaran(
        kritik: String,
        saran: String,
        namaPengirim: String,
        emailPengirim: String
    ) {
        val request = apiService.kritikSaranPengguna(kritik, saran, namaPengirim, emailPengirim)
        view?.showLoading()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.successKirim()
                        } else{
                            view?.showToast(body.message)
                            view?.hideLoading()
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