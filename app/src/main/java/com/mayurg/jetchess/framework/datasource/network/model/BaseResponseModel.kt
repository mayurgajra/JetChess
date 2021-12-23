package com.mayurg.jetchess.framework.datasource.network.model

import java.io.Serializable

data class BaseResponseModel(
    val successful: Boolean,
    val message: String
): Serializable