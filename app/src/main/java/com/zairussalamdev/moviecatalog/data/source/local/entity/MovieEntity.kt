package com.zairussalamdev.moviecatalog.data.source.local.entity

data class MovieEntity(
    val overview: String,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val id: Int,
)