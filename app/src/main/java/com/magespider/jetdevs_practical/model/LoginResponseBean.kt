package com.magespider.jetdevs_practical.model

data class LoginResponseBean(
    val errorCode: String?,
    val errorMessage: String?,
    val user: User?
)

data class User(
    val userId: String?,
    val userName: String?
)