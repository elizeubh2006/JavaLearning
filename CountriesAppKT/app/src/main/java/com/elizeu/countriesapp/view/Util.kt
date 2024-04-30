package com.elizeu.countriesapp.view

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.elizeu.countriesapp.R

//Utilizando o Coil ao invés do glide simplifica bastante o código
//O artigo abaixo explica as vantagens de usar o Coil ao invés do glide
//https://medium.com/@vijaykantkaushik/an-in-depth-comparison-of-glide-and-coil-for-efficient-image-loading-in-android-c9298016c4b0
object Util {

    fun loadImage(view: ImageView, url: String?, progressDrawable: CircularProgressDrawable) {
        view.load(url) {
            placeholder(progressDrawable)
            error(R.mipmap.ic_launcher_round)
        }
    }

    fun getProgressDrawable(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
    }
}
