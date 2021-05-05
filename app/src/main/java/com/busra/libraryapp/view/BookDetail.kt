package com.busra.libraryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.busra.libraryapp.R
import com.busra.libraryapp.model.Book
import com.busra.libraryapp.util.downloadImage
import com.busra.libraryapp.util.makePlaceHolder
import com.busra.libraryapp.viewmodel.BookDetailViewModel
import kotlinx.android.synthetic.main.fragment_book_detail.*


class BookDetail : Fragment() {
    private lateinit var viewModel : BookDetailViewModel
    private var bookid =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            bookid = BookDetailArgs.fromBundle(it).bookID
        }
        //viewModel ile fragmenti ilişkilendiriyoruz.
        viewModel = ViewModelProviders.of(this).get(BookDetailViewModel::class.java)
        viewModel.getRoomData(bookid)


        observeLiveData()
    }
    fun observeLiveData(){
        viewModel.bookLiveData.observe(viewLifecycleOwner, Observer { books->
            books?.let {
                bookName.text = it.book_name
                bookAuthor.text = it.book_author
                bookPages.text = it.book_pages
                bookYear.text = it.book_year
                bookSubject.text = it.book_subject
                context?.let {
                    bookImage.downloadImage(books.book_image, makePlaceHolder(it))
                }
            }
        })
    }

}