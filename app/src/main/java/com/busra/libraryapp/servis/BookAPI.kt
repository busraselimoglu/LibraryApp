package com.busra.libraryapp.servis

import com.busra.libraryapp.model.Book
import io.reactivex.Single
import retrofit2.http.GET

interface BookAPI {
    //https://raw.githubusercontent.com/busraselimoglu/bookjson/main/books.json
    //BASE_URL -> https://raw.githubusercontent.com/
    // busraselimoglu/bookjson/main/books.json
    @GET("busraselimoglu/bookjson/main/books.json")
    fun getBook() : Single<List<Book>>
}