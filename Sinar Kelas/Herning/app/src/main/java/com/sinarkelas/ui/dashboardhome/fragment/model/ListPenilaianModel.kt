package com.sinarkelas.ui.dashboardhome.fragment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListPenilaianModel(

    @SerializedName("idpenilaian") var idPenilaian : String? = null,
    @SerializedName("nilai") var nilai : String? = null,
    @SerializedName("judultugas") var judulTugas  : String? = null,
    @SerializedName("namalengkap") var namaLengkap : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null
) : Parcelable{
    constructor() : this(null, null, null, null, null)
}