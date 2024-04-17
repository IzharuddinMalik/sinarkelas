package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.contract.MahasiswaPresensiContract
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MahasiswaPresensiKelasPresenter(v : MahasiswaPresensiContract.mahasiswaPresensiView) : MahasiswaPresensiContract.mahasiswaPresensiPresenster {

    var apiService = ApiClient.APIService()
    var view : MahasiswaPresensiContract.mahasiswaPresensiView? = v

    override fun kirimPresensiMahasiswa(
        idPresensiKelas: String,
        idDosen: String,
        idKelas: String,
        idMahasiswa: String,
        statusPresensi : String
    ) {
        val request = apiService.mahasiswaPresensi(idPresensiKelas, idDosen, idKelas, idMahasiswa, statusPresensi)
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
                            view?.successKirim()
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