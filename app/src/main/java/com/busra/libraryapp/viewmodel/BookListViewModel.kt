package com.busra.libraryapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busra.libraryapp.model.Book
import com.busra.libraryapp.servis.BookAPIService
import com.busra.libraryapp.servis.BookDatabase
import com.busra.libraryapp.util.PrivateSharedPreferences
import com.busra.libraryapp.util.makePlaceHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

//internetten veri çekme işlemini burada yapacağım
class BookListViewModel(application: Application) : BaseViewModel(application) {
    val bookListViewModel =MutableLiveData<List<Book>>()
    val bookErrorMessageModel = MutableLiveData<Boolean>()
    val bookLoadingModel = MutableLiveData<Boolean>()
    private var updateTime = 5 * 60 * 1000 * 1000 * 1000L

    private val bookApiServices =BookAPIService()
    private val disposable = CompositeDisposable()
    private val privateSharedPreferencesList = PrivateSharedPreferences(getApplication())

    fun refreshData(){
        val recordingTime = privateSharedPreferencesList.getTime()
        if (recordingTime != null && recordingTime != 0L && System.nanoTime() - recordingTime < updateTime){
            //SQLite'tan çek
            getDataSQLite()
        }else{
            getDataInternet()
        }
    }
    fun refreshFromInternet(){
        getDataInternet()
    }
    private fun getDataSQLite(){
        launch {
            val booklist = BookDatabase(getApplication()).bookbaseDao().getALLBook()
            showBook(booklist)
        }
    }
    private fun getDataInternet(){
        bookLoadingModel.value = true

        disposable.add(
            bookApiServices.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Book>>(){
                    override fun onSuccess(t: List<Book>) {
                        // Başarılı olursa
                        hideSQLite(t)
                    }
                    override fun onError(e: Throwable) {
                        //Hata alırsak
                        bookErrorMessageModel.value = true
                        bookLoadingModel.value = false
                        e.printStackTrace()
                    }
                })
        )
    }
    private fun showBook(booksListShow : List<Book>){
        bookListViewModel.value = booksListShow
        bookErrorMessageModel.value = false
        bookLoadingModel.value =false
    }
    private fun hideSQLite(bookListSQL : List<Book>){
        launch {
            val dao = BookDatabase(getApplication()).bookbaseDao()
            dao.deleteAllBook()
            //bizden tek tek istiyordu bizde dizi olarak göndermiştik. Başına * koyarsak tek tek göndeririz
            val uuidList = dao.insertAll(*bookListSQL.toTypedArray())
            var i = 0
            while (i < bookListSQL.size){
                bookListSQL[i].uuid = uuidList[i].toInt()
                i++
            }
            showBook(bookListSQL)
        }
        privateSharedPreferencesList.saveTime(System.nanoTime())
    }
}