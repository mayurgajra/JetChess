package com.mayurg.jetchess.framework.datasource.network.ws

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.mayurg.jetchess.framework.datasource.network.ws.models.*
import com.mayurg.jetchess.util.Constants.TYPE_DISCONNECT_REQUEST
import com.mayurg.jetchess.util.Constants.TYPE_JOIN_ROOM_HANDSHAKE
import com.mayurg.jetchess.util.Constants.TYPE_MOVE
import com.mayurg.jetchess.util.Constants.TYPE_PING
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter
import com.tinder.scarlet.Scarlet
import java.lang.reflect.Type

/**
 * Message converter adapter & factory for [Scarlet] instance
 *
 * It converts message to our data class & vice-versa.
 *
 * @param gson is needed for converting string to kotlin data class & vice-versa
 *
 * Created On 11/08/2021
 * @author Mayur Gajra
 */
@Suppress("UNCHECKED_CAST")
class CustomMessageAdapter<T> private constructor(
    private val gson: Gson
) : MessageAdapter<T> {
    override fun fromMessage(message: Message): T {
        val stringValue = when (message) {
            is Message.Text -> message.value
            is Message.Bytes -> message.value.toString()
        }

        val jsonObject = JsonParser.parseString(stringValue).asJsonObject
        val type = when (jsonObject.get("type").asString) {
            TYPE_JOIN_ROOM_HANDSHAKE -> JoinRoomHandshake::class.java
            TYPE_PING -> Ping::class.java
            TYPE_DISCONNECT_REQUEST -> DisconnectRequest::class.java
            TYPE_MOVE -> GameMove::class.java
            else -> BaseModel::class.java
        }

        val obj = gson.fromJson(stringValue, type)
        return obj as T
    }

    override fun toMessage(data: T): Message {
        var convertedData = data as BaseModel
        convertedData = when (convertedData.type) {
            TYPE_JOIN_ROOM_HANDSHAKE -> convertedData as JoinRoomHandshake
            TYPE_PING -> convertedData as Ping
            TYPE_DISCONNECT_REQUEST -> convertedData as DisconnectRequest
            TYPE_MOVE -> convertedData as GameMove
            else -> convertedData
        }

        return Message.Text(gson.toJson(convertedData))

    }

    class Factory(
        private val gson: Gson
    ) : MessageAdapter.Factory {
        override fun create(type: Type, annotations: Array<Annotation>): MessageAdapter<*> {
            return CustomMessageAdapter<Any>(gson)
        }
    }
}