package com.example.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.model.Movie

class MoviePageAdapter(var onItemClick: (Movie) -> Unit) : PagingDataAdapter<Movie, MoviePageViewHolder>(MovieDiffCallBack()) {

    private val retry: () -> Unit = {}

    suspend fun set(data: PagingData<Movie>) {
            submitData(data)
        }

        override fun onBindViewHolder(holder: MoviePageViewHolder, position: Int) {
            holder.onBind(getItem(position)!!){ onItemClick(getItem(it)!!) }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            return MoviePageViewHolder(binding)
        }
    }


    class MoviePageViewHolder(
        private val binding: ItemMovieBinding,
    ) : RecyclerView.ViewHolder(binding.root) {



        fun onBind(data: Movie, onItemClick:(Id:Int)-> Unit) {
            binding.movie = data
            itemView.setOnClickListener { onItemClick(adapterPosition) }
            binding.executePendingBindings()
        }

    }



class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

