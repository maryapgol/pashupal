package com.aztechz.probeez.di

import android.content.Context
import com.aztechz.probeez.repository.profile.ProfileRepository
import com.aztechz.probeez.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object ProfileRepositoryModule {

    @Provides
    @Singleton
    fun getProfileRepository(@ApplicationContext context: Context, retrofitClient: RetrofitClient): ProfileRepository {
          return ProfileRepository(context,retrofitClient)
    }
}