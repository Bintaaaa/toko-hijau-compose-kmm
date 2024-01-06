package com.bijan.libraries.core.repository

import com.bijan.libraries.core.state.AsyncState
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

abstract class RepositoryReducer {
   inline fun <reified Response, Mapper>(suspend () -> HttpResponse).reduce(
       crossinline block: (Response) -> AsyncState<Mapper>
    ) : Flow<AsyncState<Mapper>>{
        return flow {
            val httpResponse = invoke()
            if(httpResponse.status.isSuccess()){
                val data = httpResponse.body<Response>()
                emit(block.invoke(data))
            }else{
                val throwable = Throwable(httpResponse.bodyAsText())
                val asyncFailure = AsyncState.Failure(throwable)
                emit(asyncFailure)
            }
        }.onStart {
            emit(AsyncState.Loading)
        }.catch {
            val throwable = if (it is IOException){
                Throwable("Device offline")
            }else{
                it
            }
            val asyncFailure = AsyncState.Failure(throwable)
            emit(asyncFailure)
        }
    }

}