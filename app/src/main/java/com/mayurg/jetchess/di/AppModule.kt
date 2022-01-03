package com.mayurg.jetchess.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import com.mayurg.jetchess.business.data.local.implementation.JetChessLocalDataSourceImpl
import com.mayurg.jetchess.business.data.network.NetworkConstants.BASE_URL
import com.mayurg.jetchess.business.data.network.abstraction.JetChessNetworkDataSource
import com.mayurg.jetchess.business.data.network.implementation.JetChessNetworkDataSourceImpl
import com.mayurg.jetchess.framework.datasource.network.abstraction.JetChessNetworkService
import com.mayurg.jetchess.framework.datasource.network.implementation.JetChessNetworkServiceImpl
import com.mayurg.jetchess.framework.datasource.network.mappers.LoginNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.mappers.RegisterNetworkMapper
import com.mayurg.jetchess.framework.datasource.network.retrofit.JetChessApiService
import com.mayurg.jetchess.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

    const val PREF_NAME = "jetChess_Prefs"

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

    @Singleton
    @Provides
    fun providesLocalDataSource(
        preferences: SharedPreferences,
        gson: Gson
    ): JetChessLocalDataSource {
        return JetChessLocalDataSourceImpl(
            preferences,
            gson
        )
    }

    @Singleton
    @Provides
    fun providesPrefs(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
        }
    }

}