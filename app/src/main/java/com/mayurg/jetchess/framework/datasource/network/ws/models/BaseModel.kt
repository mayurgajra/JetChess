package com.mayurg.jetchess.framework.datasource.network.ws.models

import com.mayurg.jetchess.util.Constants
import com.mayurg.jetchess.util.Constants.TYPE_PING

/**
 * Base class for all responses so that all model can be used in list or adapter
 *
 * @param type is a type of model extending BaseModel. For example [TYPE_PING]
 *
 * Other types can be found in [Constants] class
 */
abstract class BaseModel(val type: String)