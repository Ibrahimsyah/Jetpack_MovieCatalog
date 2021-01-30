package com.zairussalamdev.moviecatalog.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zairussalamdev.moviecatalog.R
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.databinding.ActivityDetailBinding
import com.zairussalamdev.moviecatalog.utils.MovieType
import com.zairussalamdev.moviecatalog.viewmodels.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CONTENT = "EXTRA_CONTENT"
        const val EXTRA_TYPE = "EXTRA_TYPE"

        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val contentId: Int = intent.getIntExtra(EXTRA_CONTENT, 0)
        val type: Int = intent.getIntExtra(EXTRA_TYPE, MovieType.TYPE_MOVIE)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(
            this,
            factory
        )[DetailViewModel::class.java]

        var isFavorite = false
        var movieDetail: DetailEntity? = null

        binding.detailProgressBar.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.getMovieDetail(type, contentId).observe(this, { detail ->
            movieDetail = detail
            supportActionBar?.title = detail?.title
            binding.detailProgressBar.visibility = View.GONE
            Glide.with(this).load("$IMAGE_BASE_URL${detail?.posterPath}")
                .placeholder(R.drawable.image_placeholder)
                .into(binding.movieDetailPoster)
            Glide.with(this).load("$IMAGE_BASE_URL${detail?.backdropPath}")
                .placeholder(R.drawable.image_placeholder)
                .into(binding.movieDetailBackdrop)
            binding.movieDetailTitle.text = detail?.title
            binding.movieDetailTagLine.text = detail?.tagLine
            binding.movieDetailRating.text = detail?.voteAverage.toString()
            binding.movieDetailDescription.text = detail?.overview
            binding.movieDetailRating.text =
                StringBuilder("${detail?.voteAverage} / 10 (${detail?.voteCount} Votes)")
            binding.movieDetailHomepage.text = detail?.homepage
            binding.movieDetailReleaseDate.text = detail?.releaseDate
            binding.movieDetailStatus.text = detail?.status
        })

        viewModel.checkMovieIsFavourite(contentId).observe(this, { it ->
            isFavorite = it
            val icon = if (isFavorite) R.drawable.ic_dislike else R.drawable.ic_like
            binding.fabFavorite.setImageResource(icon)
        })

        binding.fabFavorite.setOnClickListener {
            movieDetail?.let {
                val movie = getMovieFromDetail(it, type)
                if (isFavorite) {
                    viewModel.deleteFavoriteMovie(movie)
                    showToast(this, "Removed from Favorite")
                } else {
                    viewModel.insertFavoriteMovie(movie)
                    showToast(this, "Added to Favorite")
                }
            }
        }
    }
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun getMovieFromDetail(movieDetail: DetailEntity, movieType: Int): MovieEntity {
        return MovieEntity(
            movieDetail.id,
            movieDetail.overview,
            movieDetail.title,
            movieDetail.posterPath,
            movieDetail.voteAverage,
            movieType
        )
    }

}