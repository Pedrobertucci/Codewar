package com.code.wars.di

import android.app.Application
import com.code.wars.di.NetworkModule.provideRemoteDataSource
import com.code.wars.remoteDataSource.RemoteDataSource
import com.code.wars.repositories.DefaultRepository
import com.code.wars.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object GenericModule {

    @Provides
    fun providerRepository(context: Application): UserRepository {
        return DefaultRepository(RemoteDataSource(provideRemoteDataSource(context)))
    }
}