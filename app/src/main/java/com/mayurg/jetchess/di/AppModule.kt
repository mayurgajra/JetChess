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
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val trustAllCertificates: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    /* No-OP */
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                    /* No-OP */
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            }
        )

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertificates, SecureRandom())
        return OkHttpClient.Builder()
            .sslSocketFactory(
                sslContext.socketFactory, trustAllCertificates[0] as
                        X509TrustManager
            ).hostnameVerifier { _, _ -> true }.build()
    }

    @Singleton
    @Provides
    fun provideJetChessApi(
        okHttpClientBuilder: OkHttpClient
    ): JetChessApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClientBuilder)
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