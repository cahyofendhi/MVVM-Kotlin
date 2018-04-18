package bcr.mvvm_kotlin.repository

import android.content.Context
import bcr.mvvm_kotlin.App
import bcr.mvvm_kotlin.R
import bcr.mvvm_kotlin.model.Repository
import bcr.mvvm_kotlin.network.ApiService
import bcr.mvvm_kotlin.network.ServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver

/**
 * Created by rumahulya on 18/04/18.
 */

class MainRepository(var context: Context, var dataListener: DataListener) {

    interface DataListener {
        fun onError(message: String)
        fun onComplete()
        fun onSuccess(value: List<Repository>?)
    }

    private var factory: ServiceFactory = ServiceFactory()
    private var api: ApiService = factory.provideApi()

    /**
     * load repositories
     */
    fun loadRepositories(username : String): DisposableObserver<List<Repository>>? {
        return api.getRepositories(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(App.instance?.defaultSubscribeScheduler())
                .subscribeWith(LongOperationObserver())
    }

    private inner class LongOperationObserver: DisposableObserver<List<Repository>>() {

        override fun onComplete() {
            dataListener.onComplete()
        }

        override fun onError(e: Throwable?) {
            dataListener.onError(context?.getString(R.string.error_username_not_found))
        }

        override fun onNext(value: List<Repository>?) {
            dataListener.onSuccess(value)
        }

    }

}