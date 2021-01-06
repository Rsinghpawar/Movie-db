package com.rahul.moviedb.ui.fragments.movie

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.rahul.moviedb.R
import com.rahul.moviedb.databinding.FragmentMovieBinding
import com.rahul.moviedb.models.movie.Result


class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<MovieFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieBinding.bind(view)
        bindData()
        setUpTransition(args.data)

    }

    private fun bindData() {
        binding.result = args.data
    }

    private fun setUpTransition(result: Result) {

        binding.imgMovie.transitionName = result.posterPath
        binding.txtTitle.transitionName = result.title
        binding.txtDesc.transitionName = result.releaseDate
        binding.imgHeart.transitionName = result.popularity.toString()
        binding.imgAdult.transitionName = result.genreIds.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}