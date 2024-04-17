package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.EditAgendaContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EditAgendaPresenter(v : EditAgendaContract.editAgendaView) : EditAgendaContract.editAgendaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : EditAgendaContract.editAgendaView? = v

    override fun sendEditAgenda(
        idAgenda: String,
        judulAgenda: String,
        deskripsiAgenda: String,
        tanggalAgenda: String,
        jamAgenda: String,
        idDosen: String
    ) {
        val request = apiService.editAgenda(idAgenda, judulAgenda, deskripsiAgenda, tanggalAgenda, jamAgenda, idDosen)
        view?.showLoadingEditAgenda()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoadingEditAgenda()
                view?.showToastEditAgenda(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()

                        if (body!!.success.equals("1")){
                            view?.hideLoadingEditAgenda()
                            view?.successEditAgenda()
                        } else{
                            view?.showToastEditAgenda(body.message)
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                        view?.hideLoadingEditAgenda()
                    } catch (e : IOException){
                        e.printStackTrace()
                        view?.hideLoadingEditAgenda()
                    }
                }
            }
        })
    }

}