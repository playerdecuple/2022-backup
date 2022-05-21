package com.example.practice.api.model

data class TagList(
    var genres: List<Tag?>?
) {
    fun find(id: Int): Tag? {
        return genres?.find { it -> it?.id?.equals(id) == true };
    }
};