package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.FragDiskusiKelas
import com.sinarkelas.ui.dashboardhome.fragment.model.DetailKelasModel
import com.sinarkelas.ui.dashboardhome.fragment.model.ListInformasiModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FragDiskusiKelasPresenter(v : FragDiskusiKelas.diskusiKelasView) : FragDiskusiKelas.diskusiKelasPresenter {

    private var view : FragDiskusiKelas.diskusiKelasView? = v
    private var apiService = ApiClient.APIService()
    private var listInformasi = mutableListOf<ListInformasiModel>()

    override fun sendAllInformasi(iddosen: String, idKelas: String, idMahasiswa : String) {
        val request = apiService.getListInformasi(iddosen, idKelas, idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<ListInformasiModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<ListInformasiModel>>,
                t: Throwable
            ) {
                view?.showToast(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListInformasiModel>>,
                response: Response<WrappedListResponse<ListInformasiModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            for (i in 0 until body.info.size){
                                val info = body.info.get(i)
                                listInformasi.add(ListInformasiModel("" + info.idInformasi, "" + info.deskripsiinformasi, "" + info.idKelas, "" + info.namaLengkap, "" + info.jumlahKomentar, "" + info.createAt, "" + info.fotoProfile))
                            }
                            view?.listAllInformasi(listInformasi)
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

    override fun sendDetailKelas(iddosen: String, idKelas: String) {
        val request = apiService.getDetailKelas(iddosen, idKelas)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<DetailKelasModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<DetailKelasModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<DetailKelasModel>>,
                response: Response<WrappedListResponse<DetailKelasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.showToast(body.message)
                            for (j in 0 until body.info.size){
                                val info = body.info[j]
                                view?.detailKelas("" + info.namaKelas, "" + info.namaMataKuliah, "" + info.deskripsiKelas, "" + info.kodeKelas)
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
                }
            }
        })
    }

}