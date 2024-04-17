package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.DetailMateriUmumContract
import com.sinarkelas.ui.dashboardhome.fragment.model.DetailMateriUmumModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DetailMateriUmumPresenter(v : DetailMateriUmumContract.materiUmumView) : DetailMateriUmumContract.materiUmumPresenter {

    var apiService = ApiClient.APIService()
    var view : DetailMateriUmumContract.materiUmumView? = v

    override fun getDetailMateriUmum(idUser: String, idMateriUmum: String) {
        val request = apiService.detailMateriUmum(idUser, idMateriUmum)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<DetailMateriUmumModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<DetailMateriUmumModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<DetailMateriUmumModel>>,
                response: Response<WrappedListResponse<DetailMateriUmumModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            for (i in 0 until body.info.size){
                                val materi = body.info[i]
                                view?.detailMateri("" + materi.judulMateri, "" + materi.fileMateri, "" + materi.sumberMateri, "" + materi.tanggal, "" + materi.namaAdmin)
                            }
                        } else{
                            view?.hideLoading()
                            view?.showToast(body.message)
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                    } catch (e : IOException){
                        e.printStackTrace()
                    }
                } else{
                    val body = response.body()
                    view?.hideLoading()
                    body?.message?.let { view?.showToast(it) }
                }
            }
        })
    }

}