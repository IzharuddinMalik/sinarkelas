package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.DetailVerifikasiPembayaranContract
import com.sinarkelas.ui.upgradeakun.model.KonfirmasiPembayaranModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DetailVerifikasiPembayaranPresenter(v : DetailVerifikasiPembayaranContract.detailVerifikasiPembayaranView) : DetailVerifikasiPembayaranContract.detailVerifikasiPembayaranPresenter {

    private var apiService = ApiClient.APIService()
    private var view : DetailVerifikasiPembayaranContract.detailVerifikasiPembayaranView? = v

    override fun sendDetailVerifikasiPembayaran(idBilling: String) {
        val request = apiService.getDetailVerifikasiPembayaran(idBilling)
        view?.showDialogLoading()
        request.enqueue(object : Callback<WrappedListResponse<KonfirmasiPembayaranModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<KonfirmasiPembayaranModel>>,
                t: Throwable
            ) {
                view?.hideDialogLoading()
            }

            override fun onResponse(
                call: Call<WrappedListResponse<KonfirmasiPembayaranModel>>,
                response: Response<WrappedListResponse<KonfirmasiPembayaranModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideDialogLoading()
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            for (i in 0 until body.info.size){
                                val info = body.info.get(i)

                                view?.getDetail("" + info.namaPengirim, "" + info.createAt, "" + info.statusTransfer)
                            }
                        } else{

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