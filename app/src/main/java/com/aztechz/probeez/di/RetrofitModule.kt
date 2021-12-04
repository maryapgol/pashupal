package com.aztechz.probeez.di

import com.aztechz.probeez.retrofit.RetrofitClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {


    @Provides
    @Singleton
    fun getRetrofitBuilder(gson: Gson): Retrofit.Builder =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl("http://13.126.31.168/")

    @Provides
    @Singleton
    fun getGson() : Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun getRetrofitClient(retrofit: Retrofit.Builder): RetrofitClient
    {
        return retrofit.build().create(RetrofitClient::class.java)
    }


}