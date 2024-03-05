package com.example.aston_intensiv_3.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TelephoneData(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("personData")
    val personData: PersonData = PersonData(),
    @SerializedName("isSelected")
    val isSelected: Boolean = false
) : Serializable