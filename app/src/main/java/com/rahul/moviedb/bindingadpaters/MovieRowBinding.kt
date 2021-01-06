package com.rahul.moviedb.bindingadpaters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.rahul.moviedb.R


class MovieRowBinding {
    companion object {


        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView , imageUrl : String){
            val url = "https://image.tmdb.org/t/p/w500/$imageUrl"
            imageView.load(url){
                crossfade(600)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView , likes : Int){
            textView.text = likes.toString()
        }


        @BindingAdapter("applyAdultColor")
        @JvmStatic
        fun applyAdultColor(view: View, vegan : Boolean){
           if (vegan){
               when(view){
                   is TextView -> {
                       view.setTextColor(ContextCompat.getColor(view.context , R.color.yellow))
                       view.text = "A"
                   }
                   is ImageView -> view.setColorFilter(ContextCompat.getColor(view.context , R.color.yellow))
               }
           }
        }

    }
}