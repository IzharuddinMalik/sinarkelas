package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponseAnggotaKelas
import com.sinarkelas.contract.FragAnggotaKelasContract
import com.sinarkelas.ui.dashboardhome.fragment.model.ListAnggotaKelasModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FragAnggotaKelasPresenter(v : FragAnggotaKelasContract.anggotaKelasView) : FragAnggotaKelasContract.anggotaKelasPresenter {

    private var apiService = ApiClient.APIService()
    private var listAnggota = mutableListOf<ListAnggotaKelasModel>()
    private var view : FragAnggotaKelasContract.anggotaKelasView? = v

    override fun getAnggotaKelas(idDosen: String, idKelas: String, idMahasiswa : String) {
        val request = apiService.getListAnggotaKelas(idDosen, idKelas, idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponseAnggotaKelas<ListAnggotaKelasModel>>{
            override fun onFailure(
                call: Call<WrappedListResponseAnggotaKelas<ListAnggotaKelasModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponseAnggotaKelas<ListAnggotaKelasModel>>,
                response: Response<WrappedListResponseAnggotaKelas<ListAnggotaKelasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.getNamaDosen(body.namaDosen,body.fotoProfile)

                            for (i in 0 until body.info.size){
                                val anggota = body.info.get(i)
                                listAnggota.add(ListAnggotaKelasModel("" + anggota.namaMahasiswa, "" + anggota.fotoProfile))
                            }
                            view?.getListAnggotaKelas(listAnggota)
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