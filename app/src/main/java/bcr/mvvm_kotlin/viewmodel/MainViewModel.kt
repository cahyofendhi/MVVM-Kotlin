package bcr.mvvm_kotlin.viewmodel

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import bcr.mvvm_kotlin.App
import bcr.mvvm_kotlin.R
import bcr.mvvm_kotlin.binding.TextWatcherAdapter
import bcr.mvvm_kotlin.model.Repository
import bcr.mvvm_kotlin.network.ServiceFactory
import bcr.mvvm_kotlin.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver

/**
 * Created by rumahulya on 17/04/18.
 */


class MainViewModel(var context: Context?, var repositoryListener: RepositoryListener): BaseViewModel, MainRepository.DataListener {

    interface RepositoryListener {
        fun  onRepositoriesChanged(repositories: List<Repository>)
    }

    var infoMessage: ObservableField<String> = ObservableField<String>(context?.getString(R.string.default_info_message))
    var isInfo: ObservableBoolean = ObservableBoolean(true)
    var isProgress: ObservableBoolean = ObservableBoolean(false)
    var isEmpty: ObservableBoolean = ObservableBoolean(true)
    var isSearch: ObservableBoolean = ObservableBoolean(false)

    private var disposable: DisposableObserver<List<Repository>>? = null
    private var repositories: List<Repository>? = null
    private var mainRespository: MainRepository = MainRepository(context!!, this)

    lateinit var editTextUsernameValue: String

    fun onClickSearch(view: View) {
        loadRepositories(editTextUsernameValue)
    }

    fun getUsernameEditTextWatcher(): TextWatcher {
        return object : TextWatcherAdapter() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                editTextUsernameValue = s.toString()
                isSearch.set(s?.length!! > 0)
            }
        }
    }

    override fun destroy() {
        if(!disposable!!.isDisposed) disposable!!.dispose()
        disposable = null
        context = null
    }

    fun loadRepositories(username : String) {
        isProgress.set(true)
        isEmpty.set(true)
        isInfo.set(false)
        disposable  = mainRespository.loadRepositories(username)

    }

    override fun onError(message: String) {
        isProgress.set(false)
        infoMessage.set(message)
        isInfo.set(true)
    }

    override fun onComplete() {
        repositoryListener?.onRepositoriesChanged(repositories!!)
        isProgress.set(false)
        if (!repositories?.isEmpty()!!){
            isEmpty.set(false)
        } else {
            infoMessage.set(context?.getString(R.string.text_empty_repos))
            isInfo.set(true)
        }
    }

    override fun onSuccess(value: List<Repository>?) {
        this.repositories = value
        isProgress.set(false)
    }

}