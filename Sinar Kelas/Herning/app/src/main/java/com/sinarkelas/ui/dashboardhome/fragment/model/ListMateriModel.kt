package com.sinarkelas.ui.dashboardhome.fragment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListMateriModel(

    @SerializedName("idmaterikelas") var idMateriKelas : String? = null,
    @SerializedName("judulmateri") var judulMateri : String? = null,
    @SerializedName("namalengkap") var namaLengkap : String? = null,
    @SerializedName("create_at") var createAt : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null)
}