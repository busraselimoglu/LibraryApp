package com.busra.libraryapp.servis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.busra.libraryapp.model.Book

@Dao
interface BookDAO {
    //Insert -> Room, insert into
    //suspend -> coroutine scope
    //vararg -> birden fazla ve istediğimiz sayıda besin
    //List<Long> -> long, id'ler
    @Insert
    suspend fun insertAll(vararg book: Book) : List<Long>

    @Query("SELECT * FROM book")
    suspend fun getALLBook() : List<Book>

    // Bir tane besine ulaşmak istediğimizde
    @Query("SELECT * FROM book WHERE uuid = :bookid")
    suspend fun getBook(bookid : Int) : Book

    @Query("DELETE FROM book")
    suspend fun deleteAllBook()

    @Query("DELETE FROM book WHERE uuid = :bookid")
    suspend fun deleteBook(bookid: Int)
}