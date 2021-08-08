package com.code.wars.di

import android.app.Application
import com.code.wars.BuildConfig
import com.code.wars.remoteDataSource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    fun provideCache(context: Application) : Cache {
        return Cache(context.cacheDir, (10 * 1024 * 1024).toLong())
    }

    @Provides
    fun provideOkHttpClient(context: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .cache(provideCache(context))
            .addInterceptor(provideLoggingInterceptor())
            .build()
    }

    @Provides
    fun provideRemoteDataSource(context: Application): RemoteDataSource {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(context))
            .baseUrl(BuildConfig.baseUrl)
            .build()
            .create(RemoteDataSource::class.java)
    }
}