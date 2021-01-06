package com.rahul.moviedb.ui.fragments.nowplaying

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rahul.moviedb.adapters.MoviesNowAdapter
import com.rahul.moviedb.databinding.FragmentNowPlayingBinding
import com.rahul.moviedb.databinding.ItemMovieBinding
import com.rahul.moviedb.models.movie.Result
import com.rahul.moviedb.utils.NetworkResult
import com.rahul.moviedb.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NowPlayingFragment : Fragment(), MoviesNowAdapter.OnClick {


    private var _binding: FragmentNowPlayingBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { MoviesNowAdapter(this) }
    private val mainViewModel by viewModels<MainViewModel>()
    private var rvPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()
        readDatabase()

        return binding.root

    }


    private fun readDatabase() {
        Log.d("TAG", "readDatabase: ")
        lifecycleScope.launch {
            mainViewModel.readMovie.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].movies)
                    hideShimmer()
                } else {
                    requestApiData()
                }
            })
        }
    }


    private fun requestApiData() {
        Log.d("TAG", "requestApiData: ")
        mainViewModel.getMovies(1)
        mainViewModel.movieResponse.observe(viewLifecycleOwner, { res ->
            when (res) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    res.data?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Loading -> showShimmer()
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readMovie.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].movies)
                }
            })
        }
    }


    private fun setupRecyclerView() {
        mainViewModel.position.observe(viewLifecycleOwner, {
           rvPosition = if (it == 0 ) it else it -1
        })
        binding.recyclerView.scrollToPosition(rvPosition)
        binding.recyclerView.apply {
            this.adapter = mAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }


        showShimmer()
    }


    private fun showShimmer() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmer() {
        binding.recyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(view: ItemMovieBinding, result: Result, position: Int) {
        val direction = NowPlayingFragmentDirections.actionNowPlayingFragmentToMovieFragment(result)
        val extras = FragmentNavigatorExtras(
            view.recipeImageView to result.posterPath,
            view.titleTextView to result.title,
            view.descriptionTextView to result.releaseDate,
            view.heartImageView to result.popularity.toString(),
            view.adultImageView to result.genreIds.toString()
        )
        mainViewModel.position.value = position
        findNavController().navigate(direction, extras)


    }

}