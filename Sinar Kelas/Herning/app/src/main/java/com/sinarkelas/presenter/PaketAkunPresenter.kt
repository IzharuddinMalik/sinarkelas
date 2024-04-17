package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.PaketAkunContract
import com.sinarkelas.ui.upgradeakun.model.PaketAkunModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PaketAkunPresenter(v : PaketAkunContract.paketAkunView) : PaketAkunContract.paketAkunPresenter {

    private var view : PaketAkunContract.paketAkunView? = v
    private var apiService = ApiClient.APIService()
    private var listPaket = mutableListOf<PaketAkunModel>()

    override fun sendPaket(idUser: String) {
        val request = apiService.getPaketAkun(idUser)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<PaketAkunModel>>{
            override fun onFailure(call: Call<WrappedListResponse<PaketAkunModel>>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<PaketAkunModel>>,
                response: Response<WrappedListResponse<PaketAkunModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()

                        val body = response.body()
                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                val paket = body.info.get(i)

                                listPaket.add(PaketAkunModel("" + paket.idPaket, "" + paket.namaPaket,
                                    "" + paket.deskripsiPaket, "" + paket.hargaPaket))
                            }

                            view?.getPaket(listPaket)
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