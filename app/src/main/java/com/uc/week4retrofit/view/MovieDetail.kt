package com.uc.week4retrofit.view

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uc.week4retrofit.adapter.CompanyAdapter
import com.uc.week4retrofit.adapter.GenreAdapter
import com.uc.week4retrofit.adapter.NowPlayingAdapter
import com.uc.week4retrofit.adapter.SpokenLanguageAdapter
import com.uc.week4retrofit.databinding.ActivityMovieDetailBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.model.Genre
import com.uc.week4retrofit.model.MovieDetails
import com.uc.week4retrofit.model.SpokenLanguage
import com.uc.week4retrofit.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: GenreAdapter
    private lateinit var adapterComp: CompanyAdapter
    private lateinit var adapterSpokenLanguage: SpokenLanguageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId= intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, "Movie ID: ${movieId}", Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getMovieDetails(Const.API_KEY, movieId)

        viewModel.movieDetails.observe(this, Observer{ response->
            if (response != null){
                binding.pbDetail.visibility = View.INVISIBLE
            }
            binding.tvTitleMovieDetail.apply {
                text = response.title
            }
            Glide.with(applicationContext).load(Const.IMG_URL+response.backdrop_path).into(binding.imgPosterMovieDetail)

            val layoutManager1 =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            val layoutManager2 =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            val layoutManager3 =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

            binding.rvGenreMovieDetails.layoutManager = layoutManager1

            adapter = GenreAdapter(response.genres)

            binding.rvGenreMovieDetails.adapter = adapter

            binding.rvCompanyLogo.layoutManager = layoutManager2

            adapterComp = CompanyAdapter(response.production_companies)

            binding.rvCompanyLogo.adapter = adapterComp

            binding.rvSpokenLanguage.layoutManager = layoutManager3

            adapterSpokenLanguage = SpokenLanguageAdapter(response.spoken_languages)

            binding.rvSpokenLanguage.adapter = adapterSpokenLanguage

            binding.tvOverview.apply {
                text = response.overview
            }

        })

    }
}