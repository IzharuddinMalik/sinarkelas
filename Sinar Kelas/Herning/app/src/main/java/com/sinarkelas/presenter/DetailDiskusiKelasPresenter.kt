package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.DetailDiskusiKelasContract
import com.sinarkelas.ui.kelas.model.DetailDiskusiKelasModel
import com.sinarkelas.ui.kelas.model.ListKomentarDiskusiModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DetailDiskusiKelasPresenter(v : DetailDiskusiKelasContract.detailDiskusiView) : DetailDiskusiKelasContract.detailDiskusiPresenter {

    private var apiService = ApiClient.APIService()
    private var view : DetailDiskusiKelasContract.detailDiskusiView? = v
    private var dataListKomentar = mutableListOf<ListKomentarDiskusiModel>()

    override fun sendGetDetail(
        idKelas: String,
        idInformasi: String,
        idDosen: String,
        idMahasiswa: String
    ) {
        val request = apiService.getDetailDiskusi(idKelas, idInformasi, idDosen, idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<DetailDiskusiKelasModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<DetailDiskusiKelasModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<DetailDiskusiKelasModel>>,
                response: Response<WrappedListResponse<DetailDiskusiKelasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()

                            for (i in 0 until body.info.size){
                                var detail = body.info.get(i)
                                view?.getDetail("" + detail.idInformasi, "" + detail.deskripsiInformasi, "" + detail.fileInformasi, "" + detail.namaLengkap, ""+ detail.tanggal, ""+ detail.statusHapusInformasi, "" + detail.fotoProfile)
                            }
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

    override fun sendGetListKomentar(idKelas: String, idInformasi: String) {
        val request = apiService.getKomentarKelas(idKelas, idInformasi)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<ListKomentarDiskusiModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<ListKomentarDiskusiModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListKomentarDiskusiModel>>,
                response: Response<WrappedListResponse<ListKomentarDiskusiModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.showToast(body.message)
                            view?.hideLoading()
                            for (j in 0 until body.info.size){
                                val komentar = body.info[j]
                                dataListKomentar.add(ListKomentarDiskusiModel("" + komentar.namaLengkap, "" + komentar.komentar, "" + komentar.tanggal, "" + komentar.fotoProfile))
                            }

                            view?.getKomentar(dataListKomentar)
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

    override fun sendKomentar(
        idKelas: String,
        idDosen: String,
        idMahasiswa: String,
        komentar: String,
        idInformasi: String
    ) {
        val request = apiService.sendKomentarKelas(idKelas, idDosen, idMahasiswa, komentar, idInformasi)
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
                            view?.showToast(body.message)
                            view?.hideLoading()
                            view?.successKirimKomentar()
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