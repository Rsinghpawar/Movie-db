package com.rahul.moviedb.di

import com.rahul.moviedb.data.network.MoviesNowApi
import com.rahul.moviedb.utils.Constants.BASE_URL
import com.rahul.moviedb.utils.Constants.BASE_URL_
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideOkhttpClient() : OkHttpClient {
        return OkHttpClient.Builder().
            readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory() : GsonConverterFactory{
        return GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL_)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MoviesNowApi {
        return retrofit.create(MoviesNowApi::class.java)
    }
}