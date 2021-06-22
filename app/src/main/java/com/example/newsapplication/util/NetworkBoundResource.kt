package com.example.newsapplication.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = channelFlow {

    val data = query().first()

    if (shouldFetch(data)) {
        val loading = launch {
            query().collect {
                send(Resource.Loading(it))
            }
        }

        try {
            delay(2000)
            saveFetchResult(fetch())
            loading.cancel()
            query().collect {
                send(Resource.Success(it))
            }
        } catch (throwable: Throwable) {
            loading.cancel()
            query().collect {
                send(Resource.Error(throwable, it))
            }
        }
    } else {
        query().collect {
            send(Resource.Success(it))
        }
    }
}