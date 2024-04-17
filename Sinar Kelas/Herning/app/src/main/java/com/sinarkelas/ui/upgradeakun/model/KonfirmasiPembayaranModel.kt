package com.sinarkelas.ui.upgradeakun.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KonfirmasiPembayaranModel(

    @SerializedName("namapengirim") var namaPengirim : String?,
    @SerializedName("create_at") var createAt : String?,
    @SerializedName("statustransfer") var statusTransfer : String?
) : Parcelable{
    constructor() : this(null, null, null)
}