package com.example.practice.api.model

data class Tag(
    var id: Int?,
    var name: String?
) {
    fun from(id: Int, tl: TagList): Tag {
        val tag: Tag? = tl.find(id);
        this.id = tag?.id;
        this.name = tag?.name;

        return this;
    }
};