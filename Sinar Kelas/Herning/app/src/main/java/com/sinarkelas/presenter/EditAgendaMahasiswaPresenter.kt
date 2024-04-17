package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.EditAgendaMahasiswaContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EditAgendaMahasiswaPresenter(v : EditAgendaMahasiswaContract.editAgendaMahasiswaView) : EditAgendaMahasiswaContract.editAgendaMahasiswaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : EditAgendaMahasiswaContract.editAgendaMahasiswaView? = v

    override fun sendEditAgenda(
        idAgenda: String,
        judulAgenda: String,
        deskripsiAgenda: String,
        tanggalAgenda: String,
        jamAgenda: String,
        idMahasiswa: String
    ) {
        val request = apiService.editAgendaMahasiswa(idAgenda, judulAgenda, deskripsiAgenda, tanggalAgenda, jamAgenda, idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoading()
                view?.showToastEdit(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()

                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.successEdit()
                        } else{
                            view?.showToastEdit(body.message)
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