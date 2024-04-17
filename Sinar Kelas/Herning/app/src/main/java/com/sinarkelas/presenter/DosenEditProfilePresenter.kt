package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.DosenEditProfileContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DosenEditProfilePresenter(v : DosenEditProfileContract.dosenEditView) : DosenEditProfileContract.dosenEditPresenter {

    var apiService = ApiClient.APIService()
    var view : DosenEditProfileContract.dosenEditView? = v

    override fun editProfile(
        iddosen: String,
        namaDosen: String,
        emailDosen: String,
        passDosen: String,
        fotoProfile: String,
        jenjangPengajar : String
    ) {
        val request = apiService.dosenEditProfile(iddosen, namaDosen, emailDosen, passDosen, fotoProfile, jenjangPengajar)
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
                            view?.successEdit()
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