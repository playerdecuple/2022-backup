package com.example.practice.api.model

import com.google.gson.annotations.SerializedName

data class Movie(

    var adult: Boolean,

    @SerializedName("backdrop_path")
    var backdropPath: String,

    @SerializedName("genre_ids")
    var genreIds: List<Int>,

    var id: Int,

    @SerializedName("original_language")
    var originalLanguage: String,

    @SerializedName("original_title")
    var originalTitle: String,

    var overview: String,
    var popularity: Double,

    @SerializedName("poster_path")
    var posterPath: String,

    @SerializedName("release_date")
    var releaseDate: String,

    var title: String,
    var video: Boolean,

    @SerializedName("vote_average")
    var voteAverage: Double,

    @SerializedName("vote_count")
    var voteCount: Int

) {

    var tagList: List<Tag?>? = null;

    fun loadTag(tl: TagList): Movie {
        tagList = genreIds.map { tl.find(it) };
        return this;
    }

};