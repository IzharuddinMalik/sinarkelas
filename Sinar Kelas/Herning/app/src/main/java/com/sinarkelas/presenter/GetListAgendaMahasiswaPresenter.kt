package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.GetListAgendaMahasiswaContract
import com.sinarkelas.ui.dashboardhome.fragment.model.AgendaModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class GetListAgendaMahasiswaPresenter(v : GetListAgendaMahasiswaContract.getListAgendaMahasiswaView) : GetListAgendaMahasiswaContract.getListAgendaMahasiswaPresenter {

    private var apiService = ApiClient.APIService()
    private var view : GetListAgendaMahasiswaContract.getListAgendaMahasiswaView? = v
    private var listAgenda = mutableListOf<AgendaModel>()

    override fun sendListAgenda(idMahasiswa: String) {
        val request = apiService.getListAgendaMahasiswa(idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<AgendaModel>>{
            override fun onFailure(call: Call<WrappedListResponse<AgendaModel>>, t: Throwable) {
                view?.showToastListAgenda(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<WrappedListResponse<AgendaModel>>,
                response: Response<WrappedListResponse<AgendaModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()
                        val body = response.body()
                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                val agenda = body.info[i]

                                listAgenda.add(AgendaModel("" + agenda.idAgenda, "" + agenda.judulAgenda, "" + agenda.deskripsiAgenda,
                                    "" + agenda.tanggalAgenda, "" + agenda.jamAgenda, "" + agenda.tanggalDibuat))
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