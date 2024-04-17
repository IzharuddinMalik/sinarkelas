package com.sinarkelas.presenter

import com.sinarkelas.api.ApiClient
import com.sinarkelas.api.response.AnotherResponse
import com.sinarkelas.api.response.WrappedListResponse
import com.sinarkelas.api.response.WrappedListResponseFileTugasMhs
import com.sinarkelas.contract.DetailTugasKelasContract
import com.sinarkelas.ui.kelas.model.DetailTugasKelasModel
import com.sinarkelas.ui.kelas.model.FileTugasMahasiswaModel
import com.sinarkelas.ui.kelas.model.ListMahasiswaKumpulTugasModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DetailTugasKelasPresenter(v : DetailTugasKelasContract.detailTugasView) : DetailTugasKelasContract.detailTugasPresenter {

    var apiService = ApiClient.APIService()
    var view : DetailTugasKelasContract.detailTugasView? = v
    var data = mutableListOf<ListMahasiswaKumpulTugasModel>()

    override fun sendDetailTugas(idTugas: String, idKelas: String, idDosen: String) {
        val request = apiService.detailTugas(idTugas, idKelas, idDosen)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<DetailTugasKelasModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<DetailTugasKelasModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.showToast(t.message!!)
            }

            override fun onResponse(
                call: Call<WrappedListResponse<DetailTugasKelasModel>>,
                response: Response<WrappedListResponse<DetailTugasKelasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()

                            for (i in 0 until body.info.size){
                                val info = body.info[i]
                                view?.getDetailTugas("" + info.idTugas, "" + info.judulTugas, "" + info.deskripsiTugas, "" + info.idDosen, "" + info.namaDosen, "" + info.tenggatWaktu, "" + info.idKelas, "" + info.fileTugas, "" + info.poinTugas, "" + info.tenggatWaktu, "" + info.statusHapusTugas)
                            }
                        } else{
                            view?.hideLoading()
                            view?.showToast(body.message)
                        }
                    } catch (e : IOException){
                        e.printStackTrace()
                    } catch (e : JSONException){
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    override fun sendUploadTugas(
        iddosen: RequestBody,
        idKelas: RequestBody,
        idMahasiswa: RequestBody,
        idTugas: RequestBody,
        fileTugas: MultipartBody.Part
    ) {
        val request = apiService.mahasiswaUploadTugas(iddosen, idKelas, idMahasiswa, idTugas, fileTugas)
        view?.showLoading()
        request.enqueue(object : Callback<AnotherResponse>{
            override fun onFailure(call: Call<AnotherResponse>, t: Throwable) {
                view?.showToast(t.message!!)
                view?.hideLoading()
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
                            view?.successUploadTugas()
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

    override fun getCekStatusTugasMahasiswa(
        idTugas: String,
        idKelas: String,
        idDosen: String,
        idMahasiswa: String
    ) {
        val request = apiService.cekStatusTugasMahasiswa(idDosen, idKelas, idTugas, idMahasiswa)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponseFileTugasMhs<FileTugasMahasiswaModel>>{
            override fun onFailure(
                call: Call<WrappedListResponseFileTugasMhs<FileTugasMahasiswaModel>>,
                t: Throwable
            ) {
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<WrappedListResponseFileTugasMhs<FileTugasMahasiswaModel>>,
                response: Response<WrappedListResponseFileTugasMhs<FileTugasMahasiswaModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.hideLoading()
                            view?.showToast(body.message)

                            view?.getSelesaiTugas(body.selesaiTugas)

                            for (i in 0 until body.info.size){
                                val tugas = body.info[i]
                                view?.getFileTugas("" + tugas.fileTugas)
                            }
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

    override fun sendListTugasDikumpul(idDosen: String, idKelas: String, idTugas: String) {
        val request = apiService.listMahasiswaKumpulTugas(idDosen, idKelas, idTugas)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<ListMahasiswaKumpulTugasModel>>{
            override fun onFailure(
                call: Call<WrappedListResponse<ListMahasiswaKumpulTugasModel>>,
                t: Throwable
            ) {
                view?.showToast(t.message!!)
                view?.hideLoading()
            }

            override fun onResponse(
                call: Call<WrappedListResponse<ListMahasiswaKumpulTugasModel>>,
                response: Response<WrappedListResponse<ListMahasiswaKumpulTugasModel>>
            ) {
                if (response.isSuccessful){
                    try {
                        val body = response.body()
                        if (body!!.success.equals("1")){
                            view?.showToast(body.message)
                            view?.hideLoading()

                            for (i in 0 until body.info.size){
                                val tugas = body.info.get(i)
                                data.add(ListMahasiswaKumpulTugasModel("" + tugas.namaMahasiswa, "" + tugas.tanggal, "" + tugas.fileTugas, "" + tugas.fotoProfile))
                            }
                            view?.getListMahasiswaKumpulTugas(data)
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