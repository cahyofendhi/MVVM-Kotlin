package bcr.mvvm_kotlin.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import bcr.mvvm_kotlin.R
import bcr.mvvm_kotlin.databinding.ActivityMainBinding
import bcr.mvvm_kotlin.helper.Util
import bcr.mvvm_kotlin.model.Repository
import bcr.mvvm_kotlin.view.adapter.RepositoryAdapter
import bcr.mvvm_kotlin.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainViewModel.RepositoryListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    var adapter: RepositoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupBinding()
        setupRecycleView()
        activityView()
    }

    private fun activityView() {
        binding.editTextUsername?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val username = binding.editTextUsername.text
                    if (username.isNotEmpty())
                        viewModel.loadRepositories(username.toString())
                    return true
                }
                return false
            }
        })
    }

    private fun setupBinding() {
        viewModel   = MainViewModel(this, this)
        binding.vm  = viewModel
    }

    private fun setupRecycleView() {
        adapter = RepositoryAdapter()
        binding.reposRecyclerView.adapter = adapter
        binding.reposRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onRepositoriesChanged(repositories: List<Repository>) {
        adapter?.setRepositories(repositories)
        adapter?.notifyDataSetChanged()
        Util.hideSoftKeyboard(this, binding.editTextUsername)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }

}
