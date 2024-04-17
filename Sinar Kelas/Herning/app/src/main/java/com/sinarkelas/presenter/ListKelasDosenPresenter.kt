package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.ListKelasDosenContract
import com.sinarkelas.ui.dashboardhome.fragment.model.ListKelasModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ListKelasDosenPresenter(v : ListKelasDosenContract.kelasView) : ListKelasDosenContract.kelasPresenter{

    private var view : ListKelasDosenContract.kelasView? = v
    private var apiService = ApiClient.APIService()
    private var listKelas = mutableListOf<ListKelasModel>()

    override fun getListKelas(iddosen: String, idMahasiswa : String, statusUser : String) {
        val request = apiService.getListKelas(iddosen, idMahasiswa, statusUser)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<ListKelasModel>>{
            override fun onFailure(call: Call<WrappedListResponse<ListKelasModel>>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
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
                                val kelas = body.info[i]
                                listKelas.add(ListKelasModel("" + kelas.idKelas,"" + kelas.namaKelas, "" + kelas.deskripsiKelas, "" + kelas.idDosen, "" + kelas.namaDosen))
                            }

                            view?.listKelas(listKelas)
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