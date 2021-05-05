package com.busra.libraryapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.busra.libraryapp.R

fun ImageView.downloadImage(url : String?, placeholder : CircularProgressDrawable){
    val option = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    // context veriyoruz, neredeyse orada çalıştır mantığıyla
    Glide.with(context).setDefaultRequestOptions(option).load(url).into(this)
}
fun makePlaceHolder(context: Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        // progressbar'ın kalınlığı
        strokeWidth = 8f
        // progressbar'ın genişliği
        centerRadius = 40f
        start()
    }

}