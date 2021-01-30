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
import com.zairussalamdev.moviecatalog.ui.adapter.PagedMovieAdapter
import com.zairussalamdev.moviecatalog.ui.detail.DetailActivity
import com.zairussalamdev.moviecatalog.utils.MovieType
import com.zairussalamdev.moviecatalog.viewmodels.ViewModelFactory

class FavoriteMovieFragment() : Fragment() {

    private lateinit var movieFragmentBinding: FragmentMoviesBinding
    private lateinit var viewModel: FavoriteMovieViewModel
    private lateinit var adapter: PagedMovieAdapter
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
            viewModel = ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            adapter = PagedMovieAdapter()
            adapter.setListener {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_CONTENT, it.id)
                intent.putExtra(DetailActivity.EXTRA_TYPE, MovieType.TYPE_MOVIE)
                this.context?.startActivity(intent)
            }

            viewModel.getAllMovies().observe(viewLifecycleOwner, { movies ->
                movies?.let { movieList ->
                    movieFragmentBinding.movieListProgressbar.visibility = View.GONE
                    adapter.submitList(movieList)
                }
            })

            movieFragmentBinding.rvMovies.layoutManager = LinearLayoutManager(context)
            movieFragmentBinding.rvMovies.setHasFixedSize(true)
            movieFragmentBinding.rvMovies.adapter = adapter

        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}