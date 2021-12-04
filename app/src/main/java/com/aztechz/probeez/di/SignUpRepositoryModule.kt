package com.aztechz.probeez.di

import com.aztechz.probeez.repository.signup.SignUpRepository
import com.aztechz.probeez.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object SignUpRepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(retrofitClient: RetrofitClient): SignUpRepository
    {
        return SignUpRepository(retrofitClient)
    }
}