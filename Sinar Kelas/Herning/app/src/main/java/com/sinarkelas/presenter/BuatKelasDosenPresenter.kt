package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.BuatKelasDosenContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class BuatKelasDosenPresenter(v : BuatKelasDosenContract.buatKelasView) : BuatKelasDosenContract.buatKelasPresenter {

    private var apiService = ApiClient.APIService()
    private var view : BuatKelasDosenContract.buatKelasView? = v

    override fun buatKelas(
        namaMataKuliah: String,
        namaKelas: String,
        deskripsiKelas: String,
        idDosen: String,
        ikonKelas: String
    ) {
        val request = apiService.buatKelas(namaMataKuliah, namaKelas, deskripsiKelas, idDosen, ikonKelas)
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
                            view?.success()
                        } else{
                            view?.showToast(body.message)
                            view?.hideLoading()
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