package com.mayurg.jetchess.framework.datasource.network.ws.models

import com.mayurg.jetchess.util.Constants.TYPE_DISCONNECT_REQUEST


/**
 * Class for sending a disconnection from room request through socket.
 */
class DisconnectRequest : BaseModel(TYPE_DISCONNECT_REQUEST)