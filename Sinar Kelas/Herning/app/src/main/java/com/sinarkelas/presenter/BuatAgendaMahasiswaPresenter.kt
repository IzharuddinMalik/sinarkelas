package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.BuatAgendaMahasiswaContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BuatAgendaMahasiswaPresenter(v : BuatAgendaMahasiswaContract.buatAgendaMahasiswaView) : BuatAgendaMahasiswaContract.buatAgendaMahasiswaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : BuatAgendaMahasiswaContract.buatAgendaMahasiswaView? = v

    override fun sendAgenda(
        judulAgenda: String,
        deskripsiAgenda: String,
        tanggalAgenda: String,
        jamAgenda: String,
        idMahasiswa: String
    ) {
        val request = apiService.buatAgendaMahasiswa(judulAgenda, deskripsiAgenda, tanggalAgenda, jamAgenda, idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.showToastAgenda(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.showLoading()

                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.successBuat()
                        } else{
                            view?.showToastAgenda(body.message)
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