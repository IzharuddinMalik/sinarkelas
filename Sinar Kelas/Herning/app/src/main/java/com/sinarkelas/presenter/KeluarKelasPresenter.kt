package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.KeluarKelasContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class KeluarKelasPresenter(v : KeluarKelasContract.keluarKelasView) : KeluarKelasContract.keluarKelasPresenter {

    private var apiService = ApiClient.APIService()
    private var view : KeluarKelasContract.keluarKelasView? = v

    override fun sendKeluarKelas(idDosen: String, idMahasiswa: String, idKelas: String) {
        val request = apiService.keluarKelas(idDosen, idMahasiswa, idKelas)
        view?.showLoadingKeluarKelas()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoadingKeluarKelas()
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")){
                            view?.hideLoadingKeluarKelas()
                            view?.showToastKeluarKelas(body.message)
                            view?.successKeluar()
                        } else{
                            view?.showToastKeluarKelas(body.message)
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