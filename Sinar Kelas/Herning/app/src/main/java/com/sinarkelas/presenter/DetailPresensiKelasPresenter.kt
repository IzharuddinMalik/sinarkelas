package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.PresensiKelasResponse
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.DetailPresensiKelasContract
import com.sinarkelas.ui.kelas.model.ListDetailPresensiKelasModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DetailPresensiKelasPresenter(v : DetailPresensiKelasContract.detailPresensiKelasView) : DetailPresensiKelasContract.detailPresensiKelasPresenter {

    private var apiService = ApiClient.APIService()
    private var view : DetailPresensiKelasContract.detailPresensiKelasView? = v
    private var dataList = mutableListOf<ListDetailPresensiKelasModel>()

    override fun sendDetailPresensi(idKelas: String, idDosen: String, idPresensiKelas: String) {

        val request = apiService.detailPresensiKelas(idKelas, idDosen, idPresensiKelas)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<ListDetailPresensiKelasModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<ListDetailPresensiKelasModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListDetailPresensiKelasModel>>,
                response: Response<WrappedListResponse<ListDetailPresensiKelasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()

                            for (i in 0 until body.info.size){
                                val presensi = body.info.get(i)
                                dataList.add(ListDetailPresensiKelasModel("" + presensi.idPresensi, "" + presensi.tanggal, "" + presensi.statusPresensi, "" + presensi.namaMahasiswa, "" + presensi.fotoProfile))
                            }

                            view?.getListDetailPresensi(dataList)
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

    override fun getJumlahHadirPresensi(idKelas: String, idDosen: String, idPresensiKelas: String) {
        val request = apiService.getJumlahHadirMahasiswaPresensi(idDosen, idKelas, idPresensiKelas)
        view?.showLoading()
        request.enqueue(object : Callback<PresensiKelasResponse>{
            override fun onFailure(call: Call<PresensiKelasResponse>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<PresensiKelasResponse>,
                response: Response<PresensiKelasResponse>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.showToast(body.message)
                            view?.getJumlahHadir("" + body.hadir, "" + body.izin, "" + body.absen)
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