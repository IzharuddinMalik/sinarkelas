package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.UploadBuktiPembayaranContract
import com.sinarkelas.ui.upgradeakun.model.KonfirmasiPembayaranModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class UploadBuktiPembayaranPresenter(v : UploadBuktiPembayaranContract.uploadBuktiPembayaranView) : UploadBuktiPembayaranContract.uploadBuktiPembayaranPresenter {

    private var view : UploadBuktiPembayaranContract.uploadBuktiPembayaranView? = v
    private var apiService = ApiClient.APIService()

    override fun sendBuktiPembayaran(
        idBilling: String,
        idUser: String,
        namaPengirim: String,
        bankPengirim: String,
        tanggalTransfer: String,
        filteTransfer: String,
        statusTransfer: String
    ) {
        val request = apiService.uploadBuktiPembayaran(idBilling, idUser, namaPengirim, bankPengirim, tanggalTransfer, filteTransfer, statusTransfer)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<KonfirmasiPembayaranModel>>{
            override fun onFailure(call: Call<WrappedListResponse<KonfirmasiPembayaranModel>>, t: Throwable) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<KonfirmasiPembayaranModel>>,
                response: Response<WrappedListResponse<KonfirmasiPembayaranModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()

                        val body = response.body()

                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                val info = body.info.get(i)

                                view?.successUpload(info.namaPengirim!!, info.createAt!!)
                            }
                        } else{
                            view?.showToast(body.message)
                        }
                    } catch (e : JSONException){
                        e.printStackTrace()
                        view?.hideLoading()
                    } catch (e : IOException){
                        e.printStackTrace()
                        view?.hideLoading()
                    }
                }
            }
        })
    }

}