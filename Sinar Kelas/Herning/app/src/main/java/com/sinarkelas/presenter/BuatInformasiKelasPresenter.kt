package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.BuatInformasiKelasContract
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BuatInformasiKelasPresenter(v : BuatInformasiKelasContract.buatInformasiView) : BuatInformasiKelasContract.buatInformasiPresenter {

    private var apiService = ApiClient.APIService()
    private var view : BuatInformasiKelasContract.buatInformasiView? = v

    override fun sendBuatInformasi(
        deskripsiInformasi: RequestBody,
        idKelas: RequestBody,
        idDosen: RequestBody,
        idMahasiswa : RequestBody,
        fileInformasi: MultipartBody.Part,
        statusHapusInformasi: RequestBody
    ) {
        val request = apiService.buatInformasiKelas(deskripsiInformasi, idKelas, idDosen, idMahasiswa, fileInformasi, statusHapusInformasi)
        view?.showLoading()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.showToast(t.message!!)
                view?.hideLoading()
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
                            view?.showToast(body.message)
                            view?.hideLoading()
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                        view?.hideLoading()
                    } catch (e : IOException){
                        e.printStackTrace()
                        view?.hideLoading()
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