package com.lexiread.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lexiread.app.R
import com.lexiread.app.databinding.ItemBookCardBinding
import com.lexiread.app.domain.model.Book

/**
 * RecyclerView adapter for displaying books in a grid layout.
 * Uses ListAdapter + DiffUtil for efficient, animated updates.
 */
class BookAdapter(
    private val onBookClick: (Book) -> Unit,
    private val onBookLongClick: (Book, android.view.View) -> Unit
) : ListAdapter<BookAdapterItem, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BookViewHolder(
        private val binding: ItemBookCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookAdapterItem) {
            val book = item.book

            binding.tvBookTitle.text = book.title
            binding.tvBookAuthor.text = book.author
            binding.tvProgress.text = "${item.progressPercent}%"
            binding.progressBar.progress = item.progressPercent

            // Format badge
            binding.tvFormat.text = book.format
            binding.tvFormat.visibility = android.view.View.VISIBLE

            // Cover image via Glide
            if (!book.coverImagePath.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(book.coverImagePath)
                    .placeholder(R.drawable.ic_book_placeholder)
                    .error(R.drawable.ic_book_placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(binding.ivBookCover)
            } else {
                binding.ivBookCover.setImageResource(R.drawable.ic_book_placeholder)
            }

            // Click
            binding.root.setOnClickListener { onBookClick(book) }

            // Long-press
            binding.root.setOnLongClickListener { view ->
                onBookLongClick(book, view)
                true
            }
        }
    }
}

/**
 * Wrapper to pair a Book with its reading progress percentage.
 */
data class BookAdapterItem(
    val book: Book,
    val progressPercent: Int = 0
)

class BookDiffCallback : DiffUtil.ItemCallback<BookAdapterItem>() {
    override fun areItemsTheSame(oldItem: BookAdapterItem, newItem: BookAdapterItem): Boolean =
        oldItem.book.id == newItem.book.id

    override fun areContentsTheSame(oldItem: BookAdapterItem, newItem: BookAdapterItem): Boolean =
        oldItem == newItem
}
