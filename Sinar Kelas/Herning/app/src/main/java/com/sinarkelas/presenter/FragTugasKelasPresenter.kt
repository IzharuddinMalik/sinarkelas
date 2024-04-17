package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.FragTugasKelas
import com.sinarkelas.ui.dashboardhome.fragment.model.ListTugasModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FragTugasKelasPresenter(v : FragTugasKelas.tugasKelasView) : FragTugasKelas.tugasPresenter {

    private var view : FragTugasKelas.tugasKelasView? = v
    private var apiService = ApiClient.APIService()
    private var listTugas = mutableListOf<ListTugasModel>()

    override fun getTugasKelas(idDosen: String, idKelas: String, idMahasiswa : String) {
        val request = apiService.getListTugas(idDosen, idKelas, idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<ListTugasModel>>{
            override fun onFailure(call: Call<WrappedListResponse<ListTugasModel>>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListTugasModel>>,
                response: Response<WrappedListResponse<ListTugasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        var body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            for (i in 0 until body.info.size){
                                val tugas = body.info[i]
                                listTugas.add(ListTugasModel("" + tugas.idTugas, "" + tugas.judulTugas, "" + tugas.namaLengkap, "" + tugas.createAt))
                            }

                            view?.listTugas(listTugas)
                        } else{
                            view?.hideLoading()
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