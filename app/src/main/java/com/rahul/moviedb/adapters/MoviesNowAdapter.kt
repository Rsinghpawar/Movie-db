package com.rahul.moviedb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rahul.moviedb.databinding.ItemMovieBinding
import com.rahul.moviedb.models.movie.NowPlayingMovies
import com.rahul.moviedb.models.movie.Result
import com.rahul.moviedb.utils.MovieDiffUtil

class MoviesNowAdapter : RecyclerView.Adapter<MoviesNowAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(result: Result) {
                binding.result = result
                binding.executePendingBindings()
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater , parent , false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int = recipes.size

    fun setData(newData : NowPlayingMovies) {
        val recipesDiffUtil = MovieDiffUtil(recipes , newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes  = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}