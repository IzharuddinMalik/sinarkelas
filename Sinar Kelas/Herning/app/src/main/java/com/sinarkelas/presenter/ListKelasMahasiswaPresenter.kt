package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.ListKelasMahasiswaContract
import com.sinarkelas.ui.dashboardhome.fragment.model.ListKelasModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ListKelasMahasiswaPresenter(v : ListKelasMahasiswaContract.listKelasMhsView) : ListKelasMahasiswaContract.listKelasMhsPresenter {

    var apiService = ApiClient.APIService()
    var view : ListKelasMahasiswaContract.listKelasMhsView? = v
    var listKelas = mutableListOf<ListKelasModel>()

    override fun getListKelasMhs(iddosen: String, idMahasiswa: String, statusUser: String) {
        val request = apiService.getListKelas(iddosen, idMahasiswa, statusUser)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<ListKelasModel>>{
            override fun onFailure(call: Call<WrappedListResponse<ListKelasModel>>, t: Throwable) {
                view?.showToast(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListKelasModel>>,
                response: Response<WrappedListResponse<ListKelasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()

                            for (i in 0 until body.info.size){
                                val info = body.info.get(i)
                                listKelas.add(ListKelasModel("" + info.idKelas, "" + info.namaKelas, "" + info.deskripsiKelas, "" + info.idDosen, "" + info.namaDosen))
                            }

                            view?.getListKelas(listKelas)
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