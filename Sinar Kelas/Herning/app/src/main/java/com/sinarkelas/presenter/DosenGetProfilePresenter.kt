package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.contract.DosenGetProfileContract
import com.sinarkelas.ui.dashboardhome.profile.model.DosenProfileModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DosenGetProfilePresenter(v : DosenGetProfileContract.dosenProfileView) : DosenGetProfileContract.dosenProfilePresenter {

    var apiService = ApiClient.APIService()
    var view : DosenGetProfileContract.dosenProfileView? = v

    override fun getProfile(idDosen: String) {
        val request = apiService.dosenGetProfile(idDosen)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<DosenProfileModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<DosenProfileModel>>,
                t: Throwable
            ) {
                view?.showToast(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<WrappedListResponse<DosenProfileModel>>,
                response: Response<WrappedListResponse<DosenProfileModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        var body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()

                            for (i in 0 until body.info.size){
                                var profile = body.info.get(i)
                                view?.getProfileDosen("" + profile.namaDosen, "" + profile.emailDosen, "" + profile.fotoProfile, "" + profile.endDate, "" + profile.statusAkun)
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