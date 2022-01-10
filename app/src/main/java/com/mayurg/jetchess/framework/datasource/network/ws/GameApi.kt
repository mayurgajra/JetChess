package com.mayurg.jetchess.framework.datasource.network.ws

import com.mayurg.jetchess.framework.datasource.network.ws.models.BaseModel
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

/**
 * It contains the socket calls used for drawing game
 *
 * Connection for Websockets is established via [Scarlet]
 *
 * Created On 03/01/2022
 * @author Mayur Gajra
 */
interface GameApi {

    /**
     * It receives the events related to [WebSocket] Like OnConnectionOpened,closed etc
     *
     * @return Flow of [WebSocket.Event] to be observed
     */
    @Receive
    fun observeEvents(): Flow<WebSocket.Event>

    /**
     * It send the [baseModel] data to server.
     */
    @Send
    fun sendBaseModel(baseModel: BaseModel)

    /**
     * It receives the app related events sent from the server.
     *
     * @return Flow of [BaseModel] to be observed
     */
    @Receive
    fun observeBaseModels(): Flow<BaseModel>

}