package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.WrappedListResponseInfoDashboardMahasiswa
import com.sinarkelas.contract.DashboardHomeMahasiswaContract
import com.sinarkelas.ui.dashboardhome.MateriUmumModel
import com.sinarkelas.ui.dashboardhome.profile.model.MahasiswaProfileModel
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DashboardHomeMahasiswaPresenter(v : DashboardHomeMahasiswaContract.dashboardView) : DashboardHomeMahasiswaContract.dashboardPresenter {

    private var view : DashboardHomeMahasiswaContract.dashboardView? = v
    private var apiService = ApiClient.APIService()

    override fun sendDashboard(idMahasiswa: String) {
        val request = apiService.dataDashboardMahasiswa(idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponseInfoDashboardMahasiswa<MahasiswaProfileModel>>{
            override fun onFailure(
                call: Call<WrappedListResponseInfoDashboardMahasiswa<MahasiswaProfileModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponseInfoDashboardMahasiswa<MahasiswaProfileModel>>,
                response: Response<WrappedListResponseInfoDashboardMahasiswa<MahasiswaProfileModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        view?.hideLoading()

                        val body = response.body()
                        if (body!!.success.equals("1")){

                            for (i in 0 until body.info.size){
                                val info = body.info[i]

                                view?.getHeaderMahasiswa("" + info.namaMhs, "" + info.email, "" + info.password,
                                "" + info.fotoProfile)
                            }

                            view?.getJumlahKelas(body.jumlahKelas)
                            view?.getJumlahTugas(body.jumlahTugas)
                            view?.getJumlahAgenda(body.jumlahAgenda)
                        } else{
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