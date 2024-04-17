package com.sinarkelas.ui.kelas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailDiskusiKelasModel(

    @SerializedName("idinformasi") var idInformasi : String? = null,
    @SerializedName("deskripsiinformasi") var deskripsiInformasi : String? = null,
    @SerializedName("fileinformasi") var fileInformasi : String? = null,
    @SerializedName("namalengkap") var namaLengkap : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null,
    @SerializedName("statushapusinformasi") var statusHapusInformasi : String? = null,
    @SerializedName("fotoprofile") var fotoProfile : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null, null, null)
}