package com.zairussalamdev.moviecatalog.ui.favorite_tv_show

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.moviecatalog.databinding.FragmentTvShowsBinding
import com.zairussalamdev.moviecatalog.ui.adapter.MovieAdapter
import com.zairussalamdev.moviecatalog.ui.favorite_detail.FavoriteDetailActivity
import com.zairussalamdev.moviecatalog.utils.MovieType
import com.zairussalamdev.moviecatalog.viewmodels.ViewModelFactory

class FavoriteTvFragment() : Fragment() {
    private lateinit var tvShowsFragmentBinding: FragmentTvShowsBinding
    private lateinit var viewModel: FavoriteTvFragment
    private lateinit var adapter: FavoriteTvFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvShowsFragmentBinding = FragmentTvShowsBinding.inflate(layoutInflater, container, false)
        return tvShowsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteTvViewModel::class.java]
            val adapter = MovieAdapter()

            adapter.setListener {
                val intent = Intent(requireActivity(), FavoriteDetailActivity::class.java)
                intent.putExtra(FavoriteDetailActivity.EXTRA_CONTENT, it.id)
                intent.putExtra(FavoriteDetailActivity.EXTRA_TYPE, MovieType.TYPE_TV_SHOW)
                this.context?.startActivity(intent)
            }
            viewModel.getAllTvShows().observe(viewLifecycleOwner, { tvShows ->
                tvShows?.let { tvShowList ->
                    tvShowsFragmentBinding.tvShowsProgressbar.visibility = View.GONE
                    adapter.setMovies(tvShowList)
                    adapter.notifyDataSetChanged()
                }
            })
            with(tvShowsFragmentBinding.rvTvShows) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }
}