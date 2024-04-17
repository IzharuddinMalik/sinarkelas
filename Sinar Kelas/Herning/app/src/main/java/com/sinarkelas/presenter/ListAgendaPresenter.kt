package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.ListAgendaContract
import com.sinarkelas.ui.dashboardhome.fragment.model.AgendaModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ListAgendaPresenter(v : ListAgendaContract.listAgendaView) : ListAgendaContract.listAgendaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : ListAgendaContract.listAgendaView? = v
    private var listAgenda = mutableListOf<AgendaModel>()

    override fun sendListAgenda(idDosen: String) {
        val request = apiService.getListAgenda(idDosen)
        view?.showLoadingListAgenda()
        request.enqueue(object : Callback<WrappedListResponse<AgendaModel>>{
            override fun onFailure(call: Call<WrappedListResponse<AgendaModel>>, t: Throwable) {
                view?.hideLoadingListAgenda()
                view?.showToastListAgenda(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<AgendaModel>>,
                response: Response<WrappedListResponse<AgendaModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoadingListAgenda()

                        val body = response.body()

                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                val agenda = body.info.get(i)

                                listAgenda.add(AgendaModel("" + agenda.idAgenda, "" + agenda.judulAgenda, "" + agenda.deskripsiAgenda, "" + agenda.tanggalAgenda, "" + agenda.jamAgenda, "" + agenda.tanggalDibuat))
                            }

                            view?.getListAgenda(listAgenda)
                        } else{
                            view?.showToastListAgenda(body.message)
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