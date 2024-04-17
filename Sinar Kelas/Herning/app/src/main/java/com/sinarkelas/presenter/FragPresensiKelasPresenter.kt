package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.FragPresensiKelasContract
import com.sinarkelas.ui.kelas.model.ListPresensiKelasModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FragPresensiKelasPresenter( v : FragPresensiKelasContract.presensiKelasView) : FragPresensiKelasContract.presensiKelasPresenter {

    private var apiService = ApiClient.APIService()
    private var view : FragPresensiKelasContract.presensiKelasView? = v
    private var dataList = mutableListOf<ListPresensiKelasModel>()

    override fun sendGetList(idDosen: String, idKelas: String) {
        val request = apiService.getListPresensiKelas(idDosen, idKelas)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<ListPresensiKelasModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<ListPresensiKelasModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListPresensiKelasModel>>,
                response: Response<WrappedListResponse<ListPresensiKelasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()

                            for (i in 0 until body.info.size){
                                val data = body.info.get(i)
                                dataList.add(ListPresensiKelasModel("" + data.idPresensi, "" + data.tanggal))
                            }

                            view?.getList(dataList)
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