package com.busra.libraryapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//Entity yazdığımızda artık SQLite'a tablo olarak kaydedileceğini söylüyorum
@Entity
//Retrofit'e internetten gelen veriyi nasıl tanımladığımızı göstermek zorundayız
data class Book (
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val book_name : String?,
    @ColumnInfo(name = "author")
    @SerializedName("author")
    val book_author : String?,
    @ColumnInfo(name = "pages")
    @SerializedName("pages")
    val book_pages : String?,
    @ColumnInfo(name = "year")
    @SerializedName("year")
    val book_year : String?,
    @ColumnInfo(name = "subject")
    @SerializedName("subject")
    val book_subject : String?,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val book_image : String
    ) {
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}