package com.example.practice.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.practice.R
import com.example.practice.api.model.Movie
import com.example.practice.api.model.TagList

class MovieFragment(var movie: Movie, var tagList: TagList): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.popular_card, container, false);
        val genres = movie.loadTag(tagList);

        val title = view?.findViewById<TextView>(R.id.title);
        val tag = view?.findViewById<TextView>(R.id.tag);
        val poster: ImageView? = view?.findViewById<ImageView>(R.id.movie_poster);

        title?.text = movie.title;
        tag?.text = genres.tagList?.map {it?.name}?.joinToString(", ");

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
            .into(poster!!);

        return view;
    }

}