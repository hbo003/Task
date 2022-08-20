package com.smartestmedia.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.smartestmedia.task.R
import com.smartestmedia.task.databinding.CharacterLayoutBinding
import com.smartestmedia.task.model.RepoModel
import com.smartestmedia.task.utils.getNavOptions

class RepoAdapter : PagingDataAdapter<RepoModel,
        RepoAdapter.ImageViewHolder>(diffCallback), Filterable {

    var repoList: ArrayList<RepoModel> = ArrayList()
    var repoListFiltered: ArrayList<RepoModel> = ArrayList()
    var size = 0

    inner class ImageViewHolder(
        val binding: CharacterLayoutBinding
    ) : ViewHolder(binding.root)

    companion object {


        val diffCallback = object : DiffUtil.ItemCallback<RepoModel>() {
            override fun areItemsTheSame(
                oldItem: RepoModel,
                newItem: RepoModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RepoModel,
                newItem: RepoModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun getItemCount(): Int {
        if (size == 0) return this.snapshot().items.size
        if (size == 1) return repoListFiltered.size
        return this.snapshot().items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            CharacterLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        repoList = this.snapshot().items as ArrayList<RepoModel>
        repoListFiltered = repoList
        var repoModel = RepoModel()
        if (size == 2) {
            repoList = this.snapshot().items as ArrayList<RepoModel>
            repoListFiltered = repoList
            size = 0
        }
        if (repoListFiltered.isNotEmpty()) {
            if (getItem(position) != this.snapshot().items[position]) {
                size = 1
            }


            repoModel = repoListFiltered[position]

            holder.binding.apply {

                holder.itemView.apply {
                    tvDescription.text = "${repoModel.name}"
                    tvCreated.text = "${repoModel.createdAt}"
                    val imageLink = repoModel.owner?.avatarUrl
                    imageView.load(imageLink) {
                        crossfade(true)
                        crossfade(1000)
                    }
                    this.setOnClickListener {
                        findNavController().navigate(
                            R.id.detailsFragment,
                            bundleOf("details" to repoModel),
                            getNavOptions
                        )
                    }
                }

            }

        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                repoListFiltered = if (charString.isEmpty()) repoList else {
                    val filteredList = ArrayList<RepoModel>()
                    repoList
                        .filter {
                            (constraint?.let { it1 -> it.name?.contains(it1) } == true)
                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }

                return FilterResults().apply { values = repoListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                repoListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<RepoModel>
                size = 1
                notifyDataSetChanged()

                if (constraint.toString().trim().isEmpty()) {
                    size = 2
                }

            }
        }
    }


}