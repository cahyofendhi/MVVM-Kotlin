package bcr.mvvm_kotlin.network

import bcr.mvvm_kotlin.model.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by rumahulya on 18/04/18.
 */

interface ApiService {

    @GET("users/{username}/repos")
    fun getRepositories(@Path("username") username: String): Observable<List<Repository>>

}