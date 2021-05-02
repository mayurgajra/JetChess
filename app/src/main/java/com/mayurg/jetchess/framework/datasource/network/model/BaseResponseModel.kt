package com.mayurg.jetchess.framework.datasource.network.model

import java.io.Serializable

data class BaseResponseModel(
    val isSuccess: Boolean,
    val message: String
): Serializable