package com.rahul.moviedb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rahul.moviedb.databinding.ItemMovieBinding
import com.rahul.moviedb.models.movie.NowPlayingMovies
import com.rahul.moviedb.models.movie.Result
import com.rahul.moviedb.utils.MovieDiffUtil

class MoviesNowAdapter( val listener : MoviesNowAdapter.OnClick) : RecyclerView.Adapter<MoviesNowAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()


   inner class MyViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result, position: Int) {
            binding.result = result

            binding.recipeImageView.transitionName = result.posterPath
            binding.titleTextView.transitionName = result.title
            binding.descriptionTextView.transitionName = result.releaseDate
            binding.heartImageView.transitionName = result.popularity.toString()
            binding.adultImageView.transitionName = result.genreIds.toString()
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                listener.onItemClick(binding , result , layoutPosition)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe, position)
    }

    override fun getItemCount(): Int = recipes.size

    fun setData(newData: NowPlayingMovies) {
        val recipesDiffUtil = MovieDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

    interface OnClick{
        fun onItemClick(view: ItemMovieBinding, data: Result, position: Int)
    }
}