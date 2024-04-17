package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.MahasiswaEditProfileContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MahasiswaEditProfilePresenter(v : MahasiswaEditProfileContract.mhsEditProfileView) : MahasiswaEditProfileContract.mhsEditProfilePresenter {

    var apiService = ApiClient.APIService()
    var view : MahasiswaEditProfileContract.mhsEditProfileView? = v

    override fun editProfile(
        idMahasiswa: String,
        namaMahasiswa: String,
        emailMahasiswa: String,
        passMahasiswa: String,
        fotoProfile: String
    ) {
        val request = apiService.mahasiswaEditProfile(idMahasiswa, namaMahasiswa, emailMahasiswa, passMahasiswa, fotoProfile)
        view?.showLoading()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.showToast(t.message!!)
                view?.hideLoading()
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