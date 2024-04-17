package com.sinarkelas.ui.kelas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTugasKelasModel(

    @SerializedName("idtugas") var idTugas : String? = null,
    @SerializedName("judultugas") var judulTugas : String? = null,
    @SerializedName("deskripsitugas") var deskripsiTugas : String? = null,
    @SerializedName("iddosen") var idDosen : String? = null,
    @SerializedName("namadosen") var namaDosen : String? = null,
    @SerializedName("tanggal") var tanggalTugas : String? = null,
    @SerializedName("idkelas") var idKelas : String? = null,
    @SerializedName("filetugas") var fileTugas : String? = null,
    @SerializedName("pointugas") var poinTugas : String? = null,
    @SerializedName("tenggatwaktu") var tenggatWaktu : String? = null,
    @SerializedName("statushapustugas") var statusHapusTugas : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null, null, null, null, null, null,
    null)
}