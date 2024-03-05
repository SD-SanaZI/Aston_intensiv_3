package com.example.aston_intensiv_3.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PersonData(
    @SerializedName("name")
    val name: String = "Jon",
    @SerializedName("surname")
    val surname: String = "Snow",
    @SerializedName("telephoneNumber")
    val telephoneNumber: String = "80290000000"
) : Serializable