package com.mayurg.jetchess.framework.datasource.network.ws

import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.Stream
import com.tinder.scarlet.StreamAdapter
import com.tinder.scarlet.utils.getRawType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.reflect.Type

/**
 * A [StreamAdapter] Factory used to attach in [Scarlet] instance for
 * supporting service method return types other than [Stream].
 *
 * Created On 03/01/2022
 * @author Mayur Gajra
 */
class FlowStreamAdapter<T> : StreamAdapter<T, Flow<T>> {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun adapt(stream: Stream<T>): Flow<T> {
        return callbackFlow {
            stream.start(object : Stream.Observer<T> {
                override fun onComplete() {
                    close()
                }

                override fun onError(throwable: Throwable) {
                    close(cause = throwable)
                }

                override fun onNext(data: T) {
                    if (!isClosedForSend) {
                        trySend(data)
                    }
                }
            })
            awaitClose { }
        }
    }

    object Factory : StreamAdapter.Factory {
        override fun create(type: Type): StreamAdapter<Any, Any> {
            return when (type.getRawType()) {
                Flow::class.java -> FlowStreamAdapter()
                else -> throw IllegalStateException("Invalid stream adapter")
            }
        }

    }

}