package com.sinarkelas.ui.dashboardhome.fragment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListTugasModel(

    @SerializedName("idtugas") var idTugas : String? = null,
    @SerializedName("judultugas") var judulTugas : String? = null,
    @SerializedName("namalengkap") var namaLengkap : String? = null,
    @SerializedName("create_at") var createAt : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null)
}