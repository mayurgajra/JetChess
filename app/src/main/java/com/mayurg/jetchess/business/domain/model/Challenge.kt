package com.mayurg.jetchess.business.domain.model

import java.io.Serializable

data class Challenge(
    val id: String,
    val fromId: String,
    val toId: String,
): Serializable