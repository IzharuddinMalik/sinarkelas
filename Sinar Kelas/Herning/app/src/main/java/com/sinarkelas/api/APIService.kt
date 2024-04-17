package com.sinarkelas.api

import com.sinarkelas.ui.dashboardhome.fragment.model.*
import com.sinarkelas.ui.dashboardhome.profile.model.DosenProfileModel
import com.sinarkelas.ui.dashboardhome.profile.model.MahasiswaProfileModel
import com.sinarkelas.ui.kelas.model.*
import com.sinarkelas.api.response.*
import com.sinarkelas.ui.notifikasi.model.NotifikasiModel
import com.sinarkelas.ui.upgradeakun.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @FormUrlEncoded
    @POST("user/register/cekemailmahasiswa")
    fun cekEmailMahasiswa(@Field("email") emailMahasiswa: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/register/cekemaildosen")
    fun cekEmailDosen(@Field("email") emailDosen: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/register/signupdosen")
    fun signUpDosen(@Field("namalengkap") namaLengkapDosen : String, @Field("email") emailDosen : String, @Field("password") passwordDosen : String) : Call<RegisResponseDosen>

    @FormUrlEncoded
    @POST("user/register/signupmahasiswa")
    fun signUpMahasiswa(@Field("namalengkap") namaLengkapMahasiswa : String, @Field("email") emailMahasiswa : String, @Field("password") passwordMahasiswa : String) : Call<RegisResponseMahasiswa>

    @FormUrlEncoded
    @POST("user/login/logindosen")
    fun loginDosen(@Field("email") emailDosen: String, @Field("password") passwordDosen: String, @Field("token") token : String) : Call<LoginResponseDosen>

    @FormUrlEncoded
    @POST("user/login/loginmahasiswa")
    fun loginMahasiswa(@Field("email") emailMahasiswa: String, @Field("password") passwordMahasiswa: String, @Field("token") token : String) : Call<LoginResponseMahasiswa>

    @FormUrlEncoded
    @POST("user/dashboard/getdashboarddosen")
    fun dataDashboard(@Field("iddosen") idDosen : String) : Call<WrappedListResponseInfoDashboardDosen<DosenProfileModel>>

    @FormUrlEncoded
    @POST("user/dashboard/getdashboardmahasiswa")
    fun dataDashboardMahasiswa(@Field("idmahasiswa") idMahasiswa : String) : Call<WrappedListResponseInfoDashboardMahasiswa<MahasiswaProfileModel>>

    @FormUrlEncoded
    @POST("user/kelas/buatkelas")
    fun buatKelas(@Field("namamatakuliah") namaMataKuliah : String, @Field("namakelas") namaKelas : String, @Field("deskripsikelas") deskripsiKelas : String, @Field("iddosen") idDosen : String, @Field("ikonkelas") ikonKelas : String) : Call<AnotherResponse>

    @Multipart
    @POST("user/kelas/buatinformasi")
    fun buatInformasiKelas(@Part("deskripsiinformasi") deskripsiInformasi : RequestBody, @Part("idkelas") idKelas : RequestBody, @Part("iddosen") idDosen : RequestBody, @Part("idmahasiswa") idMahasiswa: RequestBody, @Part fileInformasi : MultipartBody.Part, @Part("statushapusinformasi") statusHapusInformasi : RequestBody) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/kelas/buatmateri")
    fun buatMateriKelas(@Field("judulmateri") judulMateriKelas : String, @Field("deskripsimateri") deskripsiMateri : String, @Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String, @Field("fotomateri") fotoMateri : String) : Call<AnotherResponse>

    @Multipart
    @POST("user/kelas/buattugas")
    fun buatTugasKelas(@Part("judultugas") judulTugasKelas : RequestBody, @Part("deskripsitugas") deskripsiTugasKelas : RequestBody, @Part("iddosen") idDosen: RequestBody, @Part("idkelas") idKelas: RequestBody, @Part fileTugas : MultipartBody.Part, @Part("pointugas") poinTugas : RequestBody, @Part("tenggatwaktu") tenggatWaktu : RequestBody, @Part("statushapustugas") statusHapusTugas : RequestBody) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/kelas/buatpenilaian")
    fun buatPenilaianKelas(@Field("nilai") nilaiKelas : String, @Field("idtugas") idTugas : String, @Field("iddosen") idDosen : String, @Field("idmahasiswa") idMahasiswa : String, @Field("idkelas") idKelas: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/kelas/mhsjoinkelas")
    fun mahasiswaJoinKelas(@Field("kodekelas") kodeKelas : String, @Field("idmahasiswa") idMahasiswa: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/kelas/getlistkelas")
    fun getListKelas(@Field("iddosen") idDosen: String, @Field("idmahasiswa") idMahasiswa: String, @Field("statususer") statusUser : String) : Call<WrappedListResponse<ListKelasModel>>

    @FormUrlEncoded
    @POST("user/kelas/getdetailkelas")
    fun getDetailKelas(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String) : Call<WrappedListResponse<DetailKelasModel>>

    @FormUrlEncoded
    @POST("user/kelas/getlistinformasi")
    fun getListInformasi(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String, @Field("idmahasiswa") idMahasiswa: String) : Call<WrappedListResponse<ListInformasiModel>>

    @FormUrlEncoded
    @POST("user/kelas/getlisttugas")
    fun getListTugas(@Field("iddosen") idDosen : String, @Field("idkelas") idKelas : String, @Field("idmahasiswa") idMahasiswa: String) : Call<WrappedListResponse<ListTugasModel>>

    @FormUrlEncoded
    @POST("user/kelas/getlistmateri")
    fun getListMateri(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String) : Call<WrappedListResponse<ListMateriModel>>

    @FormUrlEncoded
    @POST("user/kelas/getlistpenilaian")
    fun getListPenilaian(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String) : Call<WrappedListResponse<ListPenilaianModel>>

    @FormUrlEncoded
    @POST("user/kelas/getlistanggotakelas")
    fun getListAnggotaKelas(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String, @Field("idmahasiswa") idMahasiswa: String) : Call<WrappedListResponseAnggotaKelas<ListAnggotaKelasModel>>

    @FormUrlEncoded
    @POST("user/kelas/komentarinformasi")
    fun sendKomentarKelas(@Field("idkelas") idKelas: String, @Field("iddosen") idDosen: String, @Field("idmahasiswa") idMahasiswa: String, @Field("komentar") komentar : String, @Field("idinformasi") idInformasi : String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/kelas/getkomentarkelas")
    fun getKomentarKelas(@Field("idkelas") idKelas: String, @Field("idinformasi") idInformasi: String) : Call<WrappedListResponse<ListKomentarDiskusiModel>>

    @FormUrlEncoded
    @POST("user/kelas/getdetaildiskusi")
    fun getDetailDiskusi(@Field("idkelas") idKelas: String, @Field("idinformasi") idInformasi: String, @Field("iddosen") idDosen: String, @Field("idmahasiswa") idMahasiswa: String) : Call<WrappedListResponse<DetailDiskusiKelasModel>>

    @FormUrlEncoded
    @POST("user/kelas/getlistpresensikelas")
    fun getListPresensiKelas(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String) : Call<WrappedListResponse<ListPresensiKelasModel>>

    @FormUrlEncoded
    @POST("user/kelas/getlistmhspresensikelas")
    fun detailPresensiKelas(@Field("idkelas") idKelas: String, @Field("iddosen") idDosen: String, @Field("idpresensikelas") idPresensiKelas : String) : Call<WrappedListResponse<ListDetailPresensiKelasModel>>

    @FormUrlEncoded
    @POST("user/kelas/dosencreatepresensi")
    fun buatPresensiKelas(@Field("idkelas") idKelas: String, @Field("iddosen") idDosen: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/profile/getdetailprofiledosen")
    fun dosenGetProfile(@Field("iddosen") idDosen: String) : Call<WrappedListResponse<DosenProfileModel>>

    @FormUrlEncoded
    @POST("user/profile/getdetailprofilemahasiswa")
    fun mahasiswaGetProfile(@Field("idmahasiswa") idMahasiswa: String) : Call<WrappedListResponse<MahasiswaProfileModel>>

    @FormUrlEncoded
    @POST("user/profile/editprofiledosen")
    fun dosenEditProfile(@Field("iddosen") idDosen: String, @Field("namadosen") namaLengkapDosen: String, @Field("emaildosen") emailDosen: String, @Field("passdosen") passwordDosen: String, @Field("fotoprofile") fotoProfileDosen : String, @Field("jenjangpengajar") jenjangPengajar : String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/profile/editprofilemahasiswa")
    fun mahasiswaEditProfile(@Field("idmahasiswa") idMahasiswa: String, @Field("namamahasiswa") namaLengkapMahasiswa: String, @Field("emailmahasiswa") emailMahasiswa: String, @Field("passmahasiswa") passwordMahasiswa: String, @Field("fotoprofile") fotoProfileMahasiswa : String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/kelas/getjumlahpresensi")
    fun getJumlahHadirMahasiswaPresensi(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String, @Field("idpresensikelas") idPresensiKelas: String) : Call<PresensiKelasResponse>

    @FormUrlEncoded
    @POST("user/dashboard/getdetailmateriumum")
    fun detailMateriUmum(@Field("iduser") idUser: String, @Field("idmateriumum") idMateriUmum : String) : Call<WrappedListResponse<DetailMateriUmumModel>>

    @FormUrlEncoded
    @POST("user/kelas/mahasiswapresensi")
    fun mahasiswaPresensi(@Field("idpresensikelas") idPresensiKelas: String, @Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String, @Field("idmahasiswa") idMahasiswa: String, @Field("statuspresensi") statusPresensi : String) : Call<AnotherResponse>

    @Multipart
    @POST("user/kelas/mahasiswauploadtugas")
    fun mahasiswaUploadTugas(@Part("iddosen") idDosen : RequestBody, @Part("idkelas") idKelas: RequestBody, @Part("idmahasiswa") idMahasiswa: RequestBody, @Part("idtugas") idTugas : RequestBody, @Part fileTugas : MultipartBody.Part) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/kelas/getdetailtugas")
    fun detailTugas(@Field("idtugas") idTugas: String, @Field("idkelas") idKelas: String, @Field("iddosen") idDosen: String) : Call<WrappedListResponse<DetailTugasKelasModel>>

    @FormUrlEncoded
    @POST("user/kelas/cektugasmahasiswa")
    fun cekStatusTugasMahasiswa(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String, @Field("idtugas") idTugas: String, @Field("idmahasiswa") idMahasiswa: String) : Call<WrappedListResponseFileTugasMhs<FileTugasMahasiswaModel>>

    @FormUrlEncoded
    @POST("user/kelas/listmahasiswakumpultugas")
    fun listMahasiswaKumpulTugas(@Field("iddosen") idDosen: String, @Field("idkelas") idKelas: String, @Field("idtugas") idTugas: String) : Call<WrappedListResponse<ListMahasiswaKumpulTugasModel>>

    @FormUrlEncoded
    @POST("user/kritik/kritikdansaran")
    fun kritikSaranPengguna(@Field("kritik") kritik : String, @Field("saran") saran : String, @Field("namapengirim") namaPengirim : String, @Field("emailpengirim") emailPengirim : String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/payment/getpaymentmetode")
    fun getPaymentMetode(@Field("iduser") idUser : String) : Call<WrappedListResponse<PaymentModel>>

    @FormUrlEncoded
    @POST("user/payment/getpaketuser")
    fun getPaketAkun(@Field("iduser") idUser : String) : Call<WrappedListResponse<PaketAkunModel>>

    @FormUrlEncoded
    @POST("user/payment/bayarbilling")
    fun bayarBilling(@Field("iddosen") idDosen : String, @Field("idpay") idPay : String, @Field("idpaket") idPaket : String, @Field("totalbill") totalBill : String) : Call<billingResponse>

    @FormUrlEncoded
    @POST("user/payment/gethistoribilling")
    fun getHistoriBilling(@Field("iduser") idDosen : String) : Call<WrappedListResponse<HistoriBillingModel>>

    @FormUrlEncoded
    @POST("user/payment/getdetailhistoribilling")
    fun detailBilling(@Field("idbilling") idBilling : String, @Field("iduser") idDosen : String) : Call<WrappedListResponse<DetailHistoriBillingModel>>

    @FormUrlEncoded
    @POST("user/dashboard/buatagenda")
    fun buatAgenda(@Field("judulagenda") judulAgenda : String, @Field("deskripsiagenda") deskripsiAgenda : String, @Field("tanggalagenda") tanggalAgenda : String, @Field("jamagenda") jamAgenda : String, @Field("iddosen") idDosen : String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/dashboard/getlistagenda")
    fun getListAgenda(@Field("iddosen") idDosen: String) : Call<WrappedListResponse<AgendaModel>>

    @FormUrlEncoded
    @POST("user/dashboard/editagenda")
    fun editAgenda(@Field("idagenda") idAgenda : String, @Field("judulagenda") judulAgenda: String, @Field("deskripsiagenda") deskripsiAgenda: String, @Field("tanggalagenda") tanggalAgenda: String, @Field("jamagenda") jamAgenda: String, @Field("iddosen") idDosen: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/dashboard/hapusagenda")
    fun hapusAgenda(@Field("idagenda") idAgenda : String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/payment/uploadbuktipembayaran")
    fun uploadBuktiPembayaran(@Field("idbilling") idBilling: String, @Field("iduser") idUser: String, @Field("namapengirim") namaPengirim: String, @Field("bankpengirim") bankPengirim : String, @Field("tanggaltransfer") tanggalTransfer : String, @Field("filetransfer") fileTransfer : String, @Field("statustransfer") statusTransfer : String) : Call<WrappedListResponse<KonfirmasiPembayaranModel>>

    @FormUrlEncoded
    @POST("user/dashboard/buatagendamahasiswa")
    fun buatAgendaMahasiswa(@Field("judulagendamahasiswa") judulAgenda: String, @Field("deskripsiagendamahasiswa") deskripsiAgenda: String, @Field("tanggalagendamahasiswa") tanggalAgenda: String, @Field("jamagendamahasiswa") jamAgenda: String, @Field("idmahasiswa") idMahasiswa: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/dashboard/getlistagendamahasiswa")
    fun getListAgendaMahasiswa(@Field("idmahasiswa") idMahasiswa: String) : Call<WrappedListResponse<AgendaModel>>

    @FormUrlEncoded
    @POST("user/dashboard/editagendamahasiswa")
    fun editAgendaMahasiswa(@Field("idagendamahasiswa") idAgenda: String, @Field("judulagendamahasiswa") judulAgenda: String, @Field("deskripsiagendamahasiswa") deskripsiAgenda: String, @Field("tanggalagendamahasiswa") tanggalAgenda: String, @Field("jamagendamahasiswa") jamAgenda: String, @Field("idmahasiswa") idMahasiswa: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/dashboard/hapusagendamahasiswa")
    fun hapusAgendaMahasiswa(@Field("idagendamahasiswa") idAgenda: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/payment/getdetailverifikasipembayaran")
    fun getDetailVerifikasiPembayaran(@Field("idbilling") idBilling: String) : Call<WrappedListResponse<KonfirmasiPembayaranModel>>

    @FormUrlEncoded
    @POST("user/notifikasi/getnotifikasi")
    fun getNotifikasi(@Field("iduser") idUser : String) : Call<WrappedListResponse<NotifikasiModel>>

    @FormUrlEncoded
    @POST("user/kelas/keluarkelas")
    fun keluarKelas(@Field("iddosen") idDosen: String, @Field("idmahasiswa") idMahasiswa: String, @Field("idkelas") idKelas: String) : Call<AnotherResponse>

    @FormUrlEncoded
    @POST("user/kelas/editkelas")
    fun editKelas(@Field("idkelas") idKelas: String, @Field("namamatakuliah") namaMataKuliah: String, @Field("namakelas") namaKelas: String, @Field("deskripsikelas") deskripsiKelas: String, @Field("iddosen") idDosen: String) : Call<AnotherResponse>

}