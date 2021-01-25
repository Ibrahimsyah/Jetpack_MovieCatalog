package com.zairussalamdev.moviecatalog.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @field:SerializedName("results")
    val tvShows: List<TvShow>
)
