package com.uc.week4retrofit.view

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.uc.week4retrofit.databinding.ActivityMovieDetailBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId= intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, "Movie ID: ${movieId}", Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getMovieDetails(Const.API_KEY, movieId)

        viewModel.movieDetails.observe(this, Observer{ response->
            binding.tvTitleMovieDetail.apply {
                text = response.title
            }
            binding.imgPosterMovieDetail.apply {
                val img = "https://image.tmdb.org/t/p/original/" + response.poster_path
                Glide.with(this).load(img).into(binding.imgPosterMovieDetail)
            }
        })

    }
}