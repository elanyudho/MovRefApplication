package com.elanyudho.core.data.remote.source

import com.elanyudho.core.exception.Failure
import com.elanyudho.core.extension.hasEmptyBody
import com.elanyudho.core.extension.isTotallySuccess
import com.elanyudho.core.vo.Either
import com.elanyudho.core.vo.RequestResults
import org.json.JSONException
import org.json.JSONObject

import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class RemoteSafeRequest {

    open suspend fun <T> request(apiCall: suspend () -> Response<T>) : Either<Failure, T> {
        return try {
            val response = apiCall.invoke()

            when {
                response.isTotallySuccess() -> Either.Success(response.body()!!)
                response.hasEmptyBody() -> parseError(Failure((RequestResults.DATA_NOT_MATCH), Throwable("Empty Body")))
                response.code() in 300..599 -> {
                    val errorResponse = response.errorBody()?.string()
                    val errorMessage = extractErrorMessage(errorResponse)
                    parseError(Failure(RequestResults.THERE_IS_ERROR, Throwable(errorMessage), response.code().toString()))
                }
                else -> parseError(Failure(RequestResults.UNKNOWN_ERROR, Throwable("\"Unknown error from server\"")))
            }
        } catch (throwable: Throwable) {
            when(throwable) {
                is UnknownHostException -> parseError(Failure(RequestResults.TIMEOUT,throwable))
                is ConnectException -> parseError(Failure(RequestResults.SERVER_ERROR,throwable))
                is SocketTimeoutException -> parseError(Failure(RequestResults.TIMEOUT,throwable))
                else -> parseError(Failure(RequestResults.UNKNOWN_ERROR,throwable))
            }
        }
    }

    private fun parseError(failure: Failure) : Either.Error<Failure> = Either.Error(failure)

    private fun extractErrorMessage(errorResponse: String?): String {
        return try {
            val jsonObject = JSONObject(errorResponse)
            jsonObject.getString("message")
        } catch (e: JSONException) {
            ""
        }
    }
}