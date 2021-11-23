package com.mayurg.jetchess.framework.datasource.network.model

data class ChallengeDTO(
    val id: String,
    val fromId: String,
    val toId: String,
    val fromUsername: String
)