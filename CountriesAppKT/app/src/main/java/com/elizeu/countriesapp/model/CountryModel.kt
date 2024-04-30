package com.elizeu.countriesapp.model

import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("name") val countryName: String,
    @SerializedName("capital") val capital: String,
    @SerializedName("flagPNG") val flag: String
)