package com.sinarkelas.ui.upgradeakun.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaketAkunModel(

    @SerializedName("idpaket") var idPaket : String?,
    @SerializedName("namapaket") var namaPaket : String?,
    @SerializedName("deskripsipaket") var deskripsiPaket : String?,
    @SerializedName("hargapaket") var hargaPaket : String?
) : Parcelable{
    constructor() : this(null, null, null, null)
}