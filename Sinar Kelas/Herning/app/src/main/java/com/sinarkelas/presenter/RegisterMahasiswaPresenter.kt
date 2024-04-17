package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.api.response.RegisResponseMahasiswa
import com.sinarkelas.contract.RegisterMahasiswa
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RegisterMahasiswaPresenter(v : RegisterMahasiswa.registerMhsView) : RegisterMahasiswa.registerMhsPresenter {

    private var view : RegisterMahasiswa.registerMhsView? = v
    private var apiService = ApiClient.APIService()

    override fun regisMhs(namaLengkap: String, email: String, password: String) {
        val request = apiService.signUpMahasiswa(namaLengkap, email, password)
        view?.showLoading()
        request.enqueue(object : Callback<RegisResponseMahasiswa>{
            override fun onFailure(call: Call<RegisResponseMahasiswa>, t: Throwable) {
                view?.showToast(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<RegisResponseMahasiswa>,
                response: Response<RegisResponseMahasiswa>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if(body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.success(body.namaMahasiswa)
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