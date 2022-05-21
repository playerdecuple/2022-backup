package com.example.practice.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.practice.R
import com.example.practice.api.RetrofitService
import com.example.practice.api.model.MovieList
import com.example.practice.api.model.TagList
import com.example.practice.helper.SlidePagerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.exitProcess

class HomeFragment : Fragment() {

    var retrofit: Retrofit? = null;
    var container: ViewGroup? = null;
    var frView: View? = null;

    var genres: TagList? = null;
    var popular: MovieList? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.frView = inflater.inflate(R.layout.home_fragment, container, false);
        this.retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        this.container = container;

        loadView();
        return frView;
    }


    fun loadFailure() {
        Toast.makeText(context, "로드에 실패하여 앱을 종료합니다.", Toast.LENGTH_LONG).show();
        activity?.finishAndRemoveTask();

        exitProcess(0);
    }


    private fun loadView() {
        val service = retrofit!!.create(RetrofitService::class.java);
        val call = service.getGenres();

        call.enqueue(object: Callback<TagList> {
            override fun onResponse(call: Call<TagList>, response: Response<TagList>) {
                if (response.code() == 200) {
                    genres = response.body();
                    loadPopular();
                    loadAir();
                    loadAllPopular();
                    loadRecent();
                } else {
                    loadFailure();
                }
            }
            override fun onFailure(call: Call<TagList>, t: Throwable) = loadFailure();
        });
    }


    private fun loadPopular() {
        val service = retrofit!!.create(RetrofitService::class.java);
        val call = service.getPopular();

        call.enqueue(object: Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.code() == 200) {
                    popular = response.body();

                    val movie = popular?.results?.get(0);
                    view(R.id.popular_section, MovieFragment(movie!!, genres!!));
                } else {
                    loadFailure();
                }
            }
            override fun onFailure(call: Call<MovieList>, t: Throwable) = loadFailure();
        });
    }

    private fun loadAir() {
        val service = retrofit!!.create(RetrofitService::class.java);
        val call = service.getNowPlaying();

        call.enqueue(object: Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.code() == 200) {
                    popular = response.body();

                    val movie = popular?.results?.slice(0..5)?.map {
                        MovieFragment(it!!, genres!!);
                    };
                    viewSlide(view?.findViewById(R.id.air_section), movie!!);
                } else {
                    loadFailure();
                }
            }
            override fun onFailure(call: Call<MovieList>, t: Throwable) = loadFailure();
        });
    }

    private fun loadAllPopular() {
        val service = retrofit!!.create(RetrofitService::class.java);
        val call = service.getPopular();

        call.enqueue(object: Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.code() == 200) {
                    popular = response.body();

                    val movie = popular?.results?.slice(1..6)?.map {
                        MovieFragment(it!!, genres!!);
                    };
                    viewSlide(view?.findViewById(R.id.popular_all_section), movie!!);
                } else {
                    loadFailure();
                }
            }
            override fun onFailure(call: Call<MovieList>, t: Throwable) = loadFailure();
        });
    }

    private fun loadRecent() {
        val service = retrofit!!.create(RetrofitService::class.java);
        val call = service.getNowPlaying();

        call.enqueue(object: Callback<MovieList> {

            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.code() == 200) {
                    popular = response.body();

                    val movie = popular?.results?.slice(0..5)?.map {
                        MovieFragment(it!!, genres!!);
                    };
                    viewSlide(view?.findViewById(R.id.recent_section), movie!!);
                } else {
                    loadFailure();
                }
            }
            override fun onFailure(call: Call<MovieList>, t: Throwable) = loadFailure();
            
        });
    }


    private fun view(tar: Int, fr: Fragment) {
        childFragmentManager.beginTransaction().replace(tar, fr).commit();
    }
    private fun viewSlide(tar: ViewPager?, fr: List<Fragment>) {
        val slide = SlidePagerAdapter(childFragmentManager);
        slide.items = fr;
        tar?.adapter = slide;
    }

}