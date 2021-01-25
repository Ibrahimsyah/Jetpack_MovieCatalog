package com.zairussalamdev.moviecatalog.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zairussalamdev.moviecatalog.databinding.ActivityDetailBinding
import com.zairussalamdev.moviecatalog.viewmodels.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CONTENT = "EXTRA_CONTENT"
        const val EXTRA_TYPE = "EXTRA_TYPE"

        const val TYPE_MOVIES = 1
        const val TYPE_TV_SHOWS = 2
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val contentId: Int = intent.getIntExtra(EXTRA_CONTENT, 0)
        val type: Int = intent.getIntExtra(EXTRA_TYPE, TYPE_MOVIES)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(
            this,
            factory
        )[DetailViewModel::class.java]

        binding.detailProgressBar.visibility = View.VISIBLE
        viewModel.getMovieDetail(type, contentId).observe(this, { detail ->
            supportActionBar?.title = detail?.title
            binding.detailProgressBar.visibility = View.GONE
            Glide.with(this).load("$IMAGE_BASE_URL${detail?.posterPath}")
                .into(binding.movieDetailPoster)
            Glide.with(this).load("$IMAGE_BASE_URL{detail?.backdropPath}")
                .into(binding.movieDetailBackdrop)
            binding.movieDetailTitle.text = detail?.title
            binding.movieDetailTagLine.text = detail?.tagLine
            binding.movieDetailRating.text = detail?.voteAverage.toString()
            binding.movieDetailDescription.text = detail?.overview
            binding.movieDetailRating.text = "${detail?.voteAverage} / 10 (${detail?.voteCount} Votes)"
            binding.movieDetailHomepage.text = detail?.homepage
            binding.movieDetailReleaseDate.text = detail?.releaseDate
            binding.movieDetailStatus.text = detail?.status
        })

    }

}