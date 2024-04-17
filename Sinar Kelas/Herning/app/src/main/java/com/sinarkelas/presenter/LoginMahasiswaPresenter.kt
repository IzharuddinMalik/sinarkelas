package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.LoginResponseMahasiswa
import com.sinarkelas.contract.LoginMahasiswaContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginMahasiswaPresenter(v : LoginMahasiswaContract.loginView) :
    LoginMahasiswaContract.loginPresenter {

    private var view : LoginMahasiswaContract.loginView? = v
    private var apiService = ApiClient.APIService()

    override fun loginMahasiswa(email: String, password: String, token : String) {
        val request = apiService.loginMahasiswa(email, password, token)
        view?.showLoading()
        request.enqueue(object : Callback<LoginResponseMahasiswa>{
            override fun onFailure(call: Call<LoginResponseMahasiswa>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<LoginResponseMahasiswa>,
                response: Response<LoginResponseMahasiswa>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.successLogin(body.idMahasiswa, body.statusUser)
                        } else{
                            view?.hideLoading()
                            view?.showToast(body.message)
                        }
                    } catch (e : IOException){
                        e.printStackTrace()
                    } catch (e : JSONException){
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