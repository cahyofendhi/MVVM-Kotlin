package bcr.mvvm_kotlin.network

import android.util.Log
import bcr.mvvm_kotlin.BuildConfig
import bcr.mvvm_kotlin.data.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by rumahulya on 18/04/18.
 */

class ServiceFactory {

    fun provideApi(): ApiService {
        return provideRetrofit(BASE_URL).create(ApiService::class.java)
    }

    private fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("Injector", message) })
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) BODY else NONE
        return httpLoggingInterceptor
    }

}