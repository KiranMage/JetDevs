package com.magespider.jetdevs_practical.utils

import retrofit2.HttpException

sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()

    data class CustomException(val message: String) : DataState<Nothing>()

    data class Error(val exception: HttpException) : DataState<Nothing>()

    object Loading : DataState<Nothing>()

}