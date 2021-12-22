package com.mayurg.jetchess.framework.datasource.network.model

import java.io.Serializable


data class SendChallengeResponse(
    val successful: Boolean,
    val message: String,
    val challengeId: String? = null
): Serializable
