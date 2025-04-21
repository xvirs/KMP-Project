package org.krediya.project.util


sealed class StatusResult<out T> {
    data class Success<T>(val data: T): StatusResult<T>()
    data class Error(val message: String): StatusResult<Nothing>()
}