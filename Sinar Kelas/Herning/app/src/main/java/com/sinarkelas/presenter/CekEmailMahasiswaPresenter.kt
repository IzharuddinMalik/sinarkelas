package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.CekEmailMahasiswaContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CekEmailMahasiswaPresenter(v : CekEmailMahasiswaContract.cekEmailMahasiswaView) : CekEmailMahasiswaContract.cekEmailMahasiswaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : CekEmailMahasiswaContract.cekEmailMahasiswaView? = v

    override fun sendCekEmail(email: String) {
        val request = apiService.cekEmailMahasiswa(email)
        view?.showLoadingCekEmail()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoadingCekEmail()
                view?.showToastCekEmail(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {

                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.successCekEmail(body.success)
                        } else{
                            view?.successCekEmail(body.success)
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