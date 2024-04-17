package com.sinarkelas.api.response

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    @SerializedName("success") var success : String,
    @SerializedName("message") var message : String,
    @SerializedName("info") var info : T
)

data class WrappedListResponse<T>(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("info") var info : List<T>
)

data class AnotherResponse(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String
)

data class RegisResponseDosen(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("info") var namaDosen : String
)

data class RegisResponseMahasiswa(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("info") var namaMahasiswa : String
)

data class LoginResponseDosen(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("iddosen") var idDosen : String,
    @SerializedName("statusakun") var statusAkun : String,
    @SerializedName("statususer") var statusUser : String,
    @SerializedName("tipeakun") var tipeAkun : String
)

data class LoginResponseMahasiswa(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("idmahasiswa") var idMahasiswa : String,
    @SerializedName("statususer") var statusUser: String
)

data class WrappedListResponseAnggotaKelas<T>(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("namadosen") var namaDosen : String,
    @SerializedName("fotoprofile") var fotoProfile : String,
    @SerializedName("info") var info : List<T>
)

data class PresensiKelasResponse(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("hadir") var hadir : String,
    @SerializedName("izin") var izin : String,
    @SerializedName("absen") var absen : String
)

data class WrappedListResponseFileTugasMhs<T>(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("selesaitugas") var selesaiTugas : String,
    @SerializedName("info") var info : List<T>
)

data class WrappedListResponseInfoDashboardDosen<T>(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("info") var info : List<T>,
    @SerializedName("jumlahkelas") var jumlahKelas : String,
    @SerializedName("jumlahsiswa") var jumlahSiswa : String,
    @SerializedName("jumlahtugas") var jumlahTugas : String,
    @SerializedName("jumlahagenda") var jumlahAgenda : String
)

data class WrappedListResponseInfoDashboardMahasiswa<T>(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("info") var info : List<T>,
    @SerializedName("jumlahkelas") var jumlahKelas : String,
    @SerializedName("jumlahtugas") var jumlahTugas : String,
    @SerializedName("jumlahagenda") var jumlahAgenda : String
)

data class billingResponse(
    @SerializedName("success") var success: String,
    @SerializedName("message") var message : String,
    @SerializedName("kodeTrans") var kodeTrans : String
)