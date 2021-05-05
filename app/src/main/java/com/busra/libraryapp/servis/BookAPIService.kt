package com.busra.libraryapp.servis

import com.busra.libraryapp.model.Book
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BookAPIService {
    //https://raw.githubusercontent.com/busraselimoglu/bookjson/main/books.json
    //BASE_URL -> https://raw.githubusercontent.com/
    // busraselimoglu/bookjson/main/books.json

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BookAPI::class.java)

    fun getData() : Single<List<Book>>{
        return api.getBook()
    }
}