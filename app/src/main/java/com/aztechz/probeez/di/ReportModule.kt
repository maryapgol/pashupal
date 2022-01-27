package com.aztechz.probeez.di

import android.content.Context
import com.aztechz.probeez.repository.ReportRepository
import com.aztechz.probeez.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ReportModule {


    @Provides
    @Singleton
    fun getRepository(@ApplicationContext context: Context, retrofitClient: RetrofitClient): ReportRepository
    {
        return ReportRepository(context,retrofitClient)
    }

}