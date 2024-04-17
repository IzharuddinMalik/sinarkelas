package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.api.response.RegisResponseDosen
import com.sinarkelas.contract.RegisterDosenContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RegisterDosenPresenter(v : RegisterDosenContract.registerView) : RegisterDosenContract.registerPresenter {

    private var view : RegisterDosenContract.registerView? = v
    private var apiService = ApiClient.APIService()

    override fun registerDosen(namaLengkap: String, email: String, password: String) {
        val request = apiService.signUpDosen(namaLengkap, email, password)
        view?.showLoading()
        request.enqueue(object : Callback<RegisResponseDosen>{
            override fun onFailure(call: Call<RegisResponseDosen>, t: Throwable) {
                view?.showToast(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<RegisResponseDosen>,
                response: Response<RegisResponseDosen>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.successRegis(body.namaDosen)
                        } else{
                            view?.hideLoading()
                            view?.showToast(body.message)
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                    } catch (e : IOException){
                        e.printStackTrace()
                    }
                } else{
                    val body = response.body()
                    view?.hideLoading()
                    body?.message?.let { view?.showToast(it) }
                }
            }
        })
    }

}