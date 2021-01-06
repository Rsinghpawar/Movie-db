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
import com.rahul.moviedb.R
import com.rahul.moviedb.adapters.MoviesNowAdapter
import com.rahul.moviedb.databinding.FragmentNowPlayingBinding
import com.rahul.moviedb.utils.NetworkResult
import com.rahul.moviedb.viewmodels.MainViewModel
import com.rahul.moviedb.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowPlayingFragment : Fragment(R.layout.fragment_now_playing) {

    private var _binding : FragmentNowPlayingBinding?=null
    private val binding get() = _binding!!
    private val mAdapter by lazy { MoviesNowAdapter() }
    private val mainViewModel by viewModels<MainViewModel>()
    private val recipesViewModel by viewModels<MovieViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()
//        readDatabase()
        requestApiData()
        return binding.root

    }

//    private fun readDatabase() {
//        Log.d("TAG", "readDatabase: ")
//        lifecycleScope.launch {
//            mainViewModel.readMovie.observe(viewLifecycleOwner, { database ->
//                if (database.isNotEmpty()) {
//                    mAdapter.setData(database[0].foodRecipes)
//                    hideShimmer()
//                } else {
//                    requestApiData()
//                }
//            })
//        }
//    }


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
//                    loadDataFromCache()
                    Toast.makeText(requireContext(), res.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

//    private fun loadDataFromCache() {
//        lifecycleScope.launch {
//            mainViewModel.readMovie.observe(viewLifecycleOwner, { database ->
//                if (database.isNotEmpty()) {
//                    mAdapter.setData(database[0].foodRecipes)
//                }
//            })
//        }
//    }


    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
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
}