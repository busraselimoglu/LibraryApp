package com.busra.libraryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.busra.libraryapp.R
import com.busra.libraryapp.model.Book
import com.busra.libraryapp.util.downloadImage
import com.busra.libraryapp.util.makePlaceHolder
import com.busra.libraryapp.view.BookList
import com.busra.libraryapp.view.BookListDirections
import kotlinx.android.synthetic.main.book_recycler_row.view.*

class BookRecyclerAdapter (val bookListRecycler : ArrayList<Book>) : RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder>()  {
    class BookViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.book_recycler_row,parent,false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.itemView.bookNameRow.text = bookListRecycler.get(position).book_name
        holder.itemView.bookAuthorRow.text = bookListRecycler.get(position).book_author
        //gorsel
        holder.itemView.imageviewRow.downloadImage(bookListRecycler.get(position).book_image, makePlaceHolder(holder.itemView.context))

        holder.itemView.setOnClickListener {
            val action = BookListDirections.actionBookListToBookDetail(bookListRecycler.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return bookListRecycler.size
    }
    //internetten çektiğimiz veriyi güncelleme işlemi yapıyor
    fun bookListUpdate (newbooklist : List<Book>){
        bookListRecycler.clear()
        bookListRecycler.addAll(newbooklist)
        notifyDataSetChanged()
    }
}