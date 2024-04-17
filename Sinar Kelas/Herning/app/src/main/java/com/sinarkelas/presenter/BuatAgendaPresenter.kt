package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.BuatAgendaContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BuatAgendaPresenter(v : BuatAgendaContract.buatAgendaView) : BuatAgendaContract.buatAgendaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : BuatAgendaContract.buatAgendaView? = v

    override fun buatAgenda(
        judulAgenda: String,
        deskripsiAgenda: String,
        tanggalAgenda: String,
        jamAgenda: String,
        idDosen: String
    ) {
        val request = apiService.buatAgenda(judulAgenda, deskripsiAgenda, tanggalAgenda, jamAgenda, idDosen)
        view?.showLoadingAgenda()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoadingAgenda()
                view?.showToastAgenda(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoadingAgenda()

                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.successAgenda()
                        } else{
                            view?.showToastAgenda(body.message)
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                        view?.hideLoadingAgenda()
                    } catch (e : IOException){
                        e.printStackTrace()
                        view?.hideLoadingAgenda()
                    }
                }
            }
        })
    }

}