package com.sinarkelas.ui.kelas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListKomentarDiskusiModel(

    @SerializedName("namalengkap") var namaLengkap : String? = null,
    @SerializedName("komentar") var komentar : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null,
    @SerializedName("fotoprofile") var fotoProfile : String? = null
) : Parcelable{
    constructor() : this(null, null, null)
}