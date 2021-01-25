package com.zairussalamdev.moviecatalog.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(
    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("name")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("first_air_date")
    val releaseDate: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("tagline")
    val tagLine: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null,

    @field:SerializedName("homepage")
    val homepage: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)