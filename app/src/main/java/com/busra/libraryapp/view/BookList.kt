package com.busra.libraryapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.busra.libraryapp.R
import com.busra.libraryapp.adapter.BookRecyclerAdapter
import com.busra.libraryapp.viewmodel.BookListViewModel
import kotlinx.android.synthetic.main.fragment_book_list.*


class BookList : Fragment() {
    private lateinit var viewModel : BookListViewModel
    private val recyclerBookAdapter = BookRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel ile fragmenti ilişkilendiriyoruz
        listRecycler.layoutManager = LinearLayoutManager(context)
        listRecycler.adapter= recyclerBookAdapter

        //viewModel ile fragmenti ilişkilendiriyoruz
        viewModel = ViewModelProviders.of(this).get(BookListViewModel::class.java)

        viewModel.refreshData()

        observeLiveData()

        //SwipeRefreshLayout yapıldığında ne yapacağını söylüyoruz
        swipeRefresh.setOnRefreshListener {
            loadingList.visibility = View.VISIBLE
            bookErrorMessageList.visibility = View.GONE
            listRecycler.visibility = View.GONE
            viewModel.refreshFromInternet()
            // Kodda zaten progressbar var bu yüzden swipeRefresh'in progressbar'ını kapattım
            swipeRefresh.isRefreshing = false
        }
    }
    //gözlemlemek için ayrı bir fonksiyon yazalım
    fun observeLiveData(){
        viewModel.bookListViewModel.observe(viewLifecycleOwner, Observer { books->
            books?.let {
                //recycler en başta boş arrayList oluşturmuştuk şimdi içini dolduruyoruz
                recyclerBookAdapter.bookListUpdate(books)
                listRecycler.visibility = View.VISIBLE
            }
        })
        viewModel.bookErrorMessageModel.observe(viewLifecycleOwner, Observer { errors->
            errors?.let {
                if (it){
                    bookErrorMessageList.visibility = View.VISIBLE
                    listRecycler.visibility = View.GONE
                    loadingList.visibility = View.GONE
                }else{
                    bookErrorMessageList.visibility = View.GONE
                }
            }
        })
        viewModel.bookLoadingModel.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it){
                    loadingList.visibility = View.VISIBLE
                    listRecycler.visibility = View.GONE
                    bookErrorMessageList.visibility = View.GONE
                }else{
                    loadingList.visibility = View.GONE
                }
            }
        })
    }

}