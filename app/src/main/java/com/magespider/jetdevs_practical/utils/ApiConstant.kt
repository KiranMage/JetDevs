package com.magespider.jetdevs_practical.utils

object ApiConstant {
    const val BASE_URL = "http://private-222d3-homework5.apiary-mock.com/api/"
    const val LOGIN = "login"

    interface API_RESPONSE_STATUS {
        companion object {
            const val FAILED = 0
            const val SUCCESS = 1
            const val UNVERIFIED = 2
        }
    }
}