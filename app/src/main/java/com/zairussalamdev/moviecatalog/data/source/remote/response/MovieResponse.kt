package com.zairussalamdev.moviecatalog.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    @field:SerializedName("results")
    val movies: List<Movie>
) : Parcelable