package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponseInfoDashboardDosen
import com.sinarkelas.contract.DashboardHomeDosenContract
import com.sinarkelas.ui.dashboardhome.profile.model.DosenProfileModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DashboardHomeDosenPresenter(v : DashboardHomeDosenContract.dashboardView) : DashboardHomeDosenContract.dashboardPresenter {

    private var view : DashboardHomeDosenContract.dashboardView? = v
    private var apiService = ApiClient.APIService()

    override fun getInfoDashboard(idDosen: String) {
        val request = apiService.dataDashboard(idDosen)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponseInfoDashboardDosen<DosenProfileModel>>{
            override fun onFailure(
                call: Call<WrappedListResponseInfoDashboardDosen<DosenProfileModel>>,
                t: Throwable
            ) {
                view?.showToast(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<WrappedListResponseInfoDashboardDosen<DosenProfileModel>>,
                response: Response<WrappedListResponseInfoDashboardDosen<DosenProfileModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        var body = response.body()
                        view?.hideLoading()
                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                var profile = body.info.get(i)

                                view?.dosenProfile("" + profile.namaDosen, "" + profile.fotoProfile, "" + profile.jenjangPengajar)
                            }

                            view?.jumlahKelas(body.jumlahKelas)
                            view?.jumlahSiswa(body.jumlahSiswa)
                            view?.jumlahTugas(body.jumlahTugas)
                            view?.jumlahAgenda(body.jumlahAgenda)
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