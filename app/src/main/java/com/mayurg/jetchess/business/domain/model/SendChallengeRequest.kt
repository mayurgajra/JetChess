package com.mayurg.jetchess.business.domain.model

data class SendChallengeRequest(
    val fromId: String,
    val toId: String
)
