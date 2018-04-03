package com.gbmainframe.learnersindia.utils

import com.gbmainframe.learnersindia.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ambareeshb on 18/03/18.
 * Singleton for retrofit.
 */
object RetrofitUtils {
    private var retrofit: Retrofit? = null


    fun <T> initRetrofit(apiInterface: Class<T>,baseUrl:String = BuildConfig.BASE_URL): T {
        if (retrofit == null || retrofit?.baseUrl().toString() != BuildConfig.BASE_URL) {

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build()
        }
        return retrofit!!.create(apiInterface)
    }

    /**
     * Ok http client with logging interceptor.
     * @return
     */
    private val client: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
            return client
        }
}
