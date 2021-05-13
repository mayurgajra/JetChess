package com.mayurg.jetchess.di

import com.mayurg.jetchess.business.data.network.NetworkConstants.BASE_URL
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.network.implementation.JetChessNetworkDataSourceImpl
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessNetworkService
import com.mayurg.jetchess.framework.datasource.network.implementation.JetChessNetworkServiceImpl
import com.mayurg.jetchess.framework.datasource.network.mappers.LoginNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.mappers.RegisterNetworkMapper
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
    fun providesRegisterNetworkMapper(): RegisterNetworkMapper {
        return RegisterNetworkMapper()
    }

    @Singleton
    @Provides
    fun providesLoginNetworkMapper(): LoginNetworkMapper {
        return LoginNetworkMapper()
    }

    @Singleton
    @Provides
    fun providesJetChessNetworkService(
        jetChessApiService: JetChessApiService,
        registerNetworkMapper: RegisterNetworkMapper,
        loginNetworkMapper: LoginNetworkMapper
    ): JetChessNetworkService {
        return JetChessNetworkServiceImpl(
            jetChessApiService,
            registerNetworkMapper,
            loginNetworkMapper
        )
    }


    @Singleton
    @Provides
    fun provideNetworkDataSource(
        jetChessNetworkService: JetChessNetworkService,
    ): JetChessNetworkDataSource {
        return JetChessNetworkDataSourceImpl(
            jetChessNetworkService
        )
    }

}