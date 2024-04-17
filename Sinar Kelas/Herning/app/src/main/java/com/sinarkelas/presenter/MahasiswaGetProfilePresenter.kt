package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.MahasiswaGetProfileContract
import com.sinarkelas.ui.dashboardhome.profile.model.MahasiswaProfileModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MahasiswaGetProfilePresenter(v : MahasiswaGetProfileContract.mhsProfileView) : MahasiswaGetProfileContract.mhsProfilePresenter {

    var apiService = ApiClient.APIService()
    var view : MahasiswaGetProfileContract.mhsProfileView? = v

    override fun getProfile(idMahasiswa: String) {
        val request = apiService.mahasiswaGetProfile(idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<MahasiswaProfileModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<MahasiswaProfileModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<MahasiswaProfileModel>>,
                response: Response<WrappedListResponse<MahasiswaProfileModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        var body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()

                            for (i in 0 until body.info.size){
                                var profile = body.info[i]
                                view?.getProfileMhs("" + profile.namaMhs, "" + profile.email, "" + profile.fotoProfile)
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
                } else{
                    val body = response.body()
                    view?.hideLoading()
                    body?.message?.let { view?.showToast(it) }
                }
            }
        })
    }

}