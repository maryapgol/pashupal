package com.aztechz.probeez.di

import com.aztechz.probeez.retrofit.RetrofitClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {


    @Provides
    @Singleton
    fun getRetrofitBuilder(gson: Gson): Retrofit.Builder {

        val logging = HttpLoggingInterceptor()
// set your desired log level
// set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
// add your other interceptors …
// add logging as last interceptor
// add your other interceptors …
// add logging as last interceptor
        httpClient.addInterceptor(logging) // <-- this is the important line!

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://13.126.31.168/").client(httpClient.build())

    }

    @Provides
    @Singleton
    fun getGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun getRetrofitClient(retrofit: Retrofit.Builder): RetrofitClient {
        return retrofit.build().create(RetrofitClient::class.java)
    }


}