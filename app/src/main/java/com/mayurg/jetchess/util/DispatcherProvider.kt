package com.mayurg.jetchess.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created On 03/01/2022
 * @author Mayur Gajra
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}