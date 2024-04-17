package com.sinarkelas.ui.kelas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListMahasiswaKumpulTugasModel (

    @SerializedName("namamahasiswa") var namaMahasiswa : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null,
    @SerializedName("filetugas") var fileTugas : String? = null,
    @SerializedName("fotoprofile") var fotoProfile : String? = null

) : Parcelable{
    constructor() : this(null, null, null, null)
}