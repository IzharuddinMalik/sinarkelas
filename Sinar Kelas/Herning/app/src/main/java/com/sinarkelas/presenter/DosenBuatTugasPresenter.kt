package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.DosenBuatTugasContract
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DosenBuatTugasPresenter(v : DosenBuatTugasContract.dosenBuatTugasView) : DosenBuatTugasContract.dosenBuatTugasPresenter {

    var apiService = ApiClient.APIService()
    var view : DosenBuatTugasContract.dosenBuatTugasView? = v

    override fun kirimTugas(
        judulTugas: RequestBody,
        deskripsiTugas: RequestBody,
        idDosen: RequestBody,
        idKelas: RequestBody,
        fileTugas: MultipartBody.Part,
        poinTugas: RequestBody,
        tenggatWaktu: RequestBody,
        statusHapusTugas: RequestBody
    ) {
        val request = apiService.buatTugasKelas(judulTugas, deskripsiTugas, idDosen, idKelas, fileTugas, poinTugas, tenggatWaktu, statusHapusTugas)
        view?.showLoading()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<AnotherResponse>,
                response: Response<AnotherResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.successBuat()
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