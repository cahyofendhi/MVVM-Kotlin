package bcr.mvvm_kotlin.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import bcr.mvvm_kotlin.R
import bcr.mvvm_kotlin.databinding.ItemRepoBinding
import bcr.mvvm_kotlin.model.Repository
import bcr.mvvm_kotlin.viewmodel.ItemRepoViewModel
import java.util.*

/**
 * Created by rumahulya on 18/04/18.
 */

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private var repositories: List<Repository> = Collections.emptyList()

    fun setRepositories(repositories: List<Repository>) {
        this.repositories   = repositories
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindRepository(repositories.get(position))
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemRepoBinding>(LayoutInflater.from(parent?.context),
                R.layout.item_repo, parent, false)
        return ViewHolder(binding)
    }


    class ViewHolder(var binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.cardView) {

        fun bindRepository(repository: Repository) {
            if (binding.vm == null){
                binding.vm = ItemRepoViewModel(itemView.context, repository)
            } else {
                ItemRepoViewModel.setRepository(binding.vm!!, repository)
            }
        }
    }
}