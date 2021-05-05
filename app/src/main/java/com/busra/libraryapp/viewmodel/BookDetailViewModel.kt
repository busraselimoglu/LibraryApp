package com.busra.libraryapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busra.libraryapp.model.Book
import com.busra.libraryapp.servis.BookDatabase
import kotlinx.coroutines.launch

class BookDetailViewModel(application: Application) : BaseViewModel(application) {
    val bookLiveData = MutableLiveData<Book>()

    fun getRoomData(uuid : Int){
        launch {
            val dao = BookDatabase(getApplication()).bookbaseDao()
            val book = dao.getBook(uuid)
            bookLiveData.value = book
        }
    }
}