package com.example.practice.api.model

import com.google.gson.annotations.SerializedName

data class MovieList(
    val page: Int?,
    val results: List<Movie?>? = null,

    @SerializedName("total_pages")
    val totalPages: Int?,

    @SerializedName("total_results")
    val totalResults: Int?
);