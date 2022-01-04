package com.mayurg.jetchess.framework.datasource.network.ws.models

import com.mayurg.jetchess.util.Constants.TYPE_JOIN_ROOM_HANDSHAKE


/**
 * Data class for sending request to perform a handshake
 * When a web socket connection is established with websockets
 *
 * @param username is the unique username logged into the app
 * @param roomId is the room id user has to join
 * @param playerId is logged in user id
 */
data class JoinRoomHandshake(
    val username: String,
    val roomId: String,
    val playerId: String
) : BaseModel(TYPE_JOIN_ROOM_HANDSHAKE)
