package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.EditKelasContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EditKelasPresenter(v : EditKelasContract.editKelasView) : EditKelasContract.editKelasPresenter {

    private var apiService = ApiClient.APIService()
    private var view : EditKelasContract.editKelasView? = v

    override fun sendEditKelas(
        idKelas: String,
        namaMataKuliah: String,
        namaKelas: String,
        deskripsiKelas: String,
        idDosen: String
    ) {
        val request = apiService.editKelas(idKelas, namaMataKuliah, namaKelas, deskripsiKelas, idDosen)
        view?.showLoadingEditKelas()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoadingEditKelas()
                view?.showToastEditKelas(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")){
                            view?.showToastEditKelas(body.message)
                            view?.successEditKelas()
                        } else{
                            view?.showToastEditKelas(body.message)
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