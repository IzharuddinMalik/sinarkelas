package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.LoginResponseDosen
import com.sinarkelas.contract.LoginDosenContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginDosenPresenter(v : LoginDosenContract.loginView) : LoginDosenContract.loginPresenter {

    private var view : LoginDosenContract.loginView? = v
    private var apiService = ApiClient.APIService()

    override fun loginDosen(email: String, password: String, token : String) {
        val request = apiService.loginDosen(email, password, token)
        view?.showLoading()
        request.enqueue(object : Callback<LoginResponseDosen>{
            override fun onFailure(call: Call<LoginResponseDosen>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<LoginResponseDosen>,
                response: Response<LoginResponseDosen>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.successLogin(body.idDosen, body.statusUser, body.statusAkun, body.tipeAkun)
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