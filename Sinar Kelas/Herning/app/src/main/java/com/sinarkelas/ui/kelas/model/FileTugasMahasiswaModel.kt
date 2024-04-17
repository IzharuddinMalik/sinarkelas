package com.sinarkelas.ui.kelas.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileTugasMahasiswaModel (

    @SerializedName("filetugas") var fileTugas : String? = null

) : Parcelable{
    constructor() : this(null)
}