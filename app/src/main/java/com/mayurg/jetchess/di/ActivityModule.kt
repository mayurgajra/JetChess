package com.mayurg.jetchess.di

import android.app.Application
import com.google.gson.Gson
import com.mayurg.jetchess.business.data.network.NetworkConstants
import com.mayurg.jetchess.framework.datasource.network.ws.CustomMessageAdapter
import com.mayurg.jetchess.framework.datasource.network.ws.GameApi
import com.mayurg.jetchess.framework.datasource.network.ws.FlowStreamAdapter
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.OkHttpClient

/**
 * Created On 03/01/2022
 * @author Mayur Gajra
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {


    @ActivityRetainedScoped
    @Provides
    fun provideDrawingApi(
        app: Application,
        okkHttpClient: OkHttpClient,
        gson: Gson
    ): GameApi {
        return Scarlet.Builder()
            .backoffStrategy(LinearBackoffStrategy(NetworkConstants.RECONNECT_INTERVAL))
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
            .webSocketFactory(
                okkHttpClient.newWebSocketFactory(
                    NetworkConstants.WS_BASE_URL_LOCALHOST
                )
            ).addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .addMessageAdapterFactory(CustomMessageAdapter.Factory(gson))
            .build()
            .create()
    }

}