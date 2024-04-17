package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.GetNotifikasiContract
import com.sinarkelas.ui.notifikasi.model.NotifikasiModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class GetNotifikasiPresenter(v : GetNotifikasiContract.getNotifView) : GetNotifikasiContract.getNotifPresenter {

    private var apiService = ApiClient.APIService()
    private var view : GetNotifikasiContract.getNotifView? = v
    private var listNotif = mutableListOf<NotifikasiModel>()

    override fun sendGetNotif(idUser: String) {
        val request = apiService.getNotifikasi(idUser)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<NotifikasiModel>>{
            override fun onFailure(call: Call<WrappedListResponse<NotifikasiModel>>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<NotifikasiModel>>,
                response: Response<WrappedListResponse<NotifikasiModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()

                        val body = response.body()

                        if (body!!.success.equals("1")){
                            for (i in 0 until body.info.size){
                                val info = body.info.get(i)

                                listNotif.add(NotifikasiModel("" + info.idNotifikasi, "" + info.idAdmin, "" + info.namaAdmin, "" + info.pesanNotif, "" + info.tanggalNotifikasi, "" + info.gambarNotif))
                            }

                            view?.getListNotif(listNotif)
                        } else{
                            view?.showToast(body.message)
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