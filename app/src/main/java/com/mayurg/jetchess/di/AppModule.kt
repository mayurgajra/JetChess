package com.mayurg.jetchess.di

import com.mayurg.jetchess.business.data.network.NetworkConstants.BASE_URL
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.network.implementation.JetChessNetworkDataSourceImpl
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessNetworkService
import com.mayurg.jetchess.framework.datasource.network.implementation.JetChessNetworkServiceImpl
import com.mayurg.jetchess.framework.datasource.network.mappers.NetworkMapper
import com.mayurg.jetchess.framework.datasource.network.retrofit.JetChessApiService
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

    @Singleton
    @Provides
    fun providesNetworkMapper(): NetworkMapper{
        return NetworkMapper()
    }

    @Singleton
    @Provides
    fun providesJetChessNetworkService(
        jetChessApiService: JetChessApiService,
        networkMapper: NetworkMapper
    ): JetChessNetworkService{
        return JetChessNetworkServiceImpl(jetChessApiService,networkMapper)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        jetChessNetworkService: JetChessNetworkService,
        networkMapper: NetworkMapper
    ): JetChessNetworkDataSource{
        return JetChessNetworkDataSourceImpl(jetChessNetworkService,networkMapper)
    }

}