package com.zairussalamdev.moviecatalog.ui.favorite_movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.moviecatalog.databinding.FragmentMoviesBinding
import com.zairussalamdev.moviecatalog.ui.adapter.MovieAdapter
import com.zairussalamdev.moviecatalog.ui.favorite_detail.FavoriteDetailActivity
import com.zairussalamdev.moviecatalog.utils.MovieType
import com.zairussalamdev.moviecatalog.viewmodels.ViewModelFactory

class FavoriteMovieFragment(private val showFavoriteList: Boolean = false) : Fragment() {

    private lateinit var movieFragmentBinding: FragmentMoviesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        movieFragmentBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return movieFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]
            val adapter = MovieAdapter()
            adapter.setListener {

                val intent = Intent(requireActivity(), FavoriteDetailActivity::class.java)
                intent.putExtra(FavoriteDetailActivity.EXTRA_CONTENT, it.id)
                intent.putExtra(FavoriteDetailActivity.EXTRA_TYPE, MovieType.TYPE_MOVIE)
                this.context?.startActivity(intent)
            }

            viewModel.getAllMovies().observe(viewLifecycleOwner, { movies ->
                movies?.let { movieList ->
                    movieFragmentBinding.movieListProgressbar.visibility = View.GONE
                    adapter.setMovies(movieList)
                    adapter.notifyDataSetChanged()
                }
            })

            with(movieFragmentBinding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }
}