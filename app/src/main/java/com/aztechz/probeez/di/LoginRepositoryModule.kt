package com.aztechz.probeez.di

import com.aztechz.probeez.repository.login.LoginRepository
import com.aztechz.probeez.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object LoginRepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(retrofitClient: RetrofitClient): LoginRepository
    {
        return LoginRepository(retrofitClient)
    }

}