package com.mayurg.jetchess.di

import com.mayurg.jetchess.business.data.network.NetworkConstants.BASE_URL
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJetChessApi(): JetChessApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(JetChessApiService::class.java)
    }

}