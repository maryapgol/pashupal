package com.aztechz.probeez.di

import com.aztechz.probeez.repository.task.TaskRepository
import com.aztechz.probeez.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object TaskRepositoryModule {


    @Provides
    @Singleton
    fun provideTaskRepository(restClient: RetrofitClient) : TaskRepository {
         return TaskRepository(restClient)
    }
}