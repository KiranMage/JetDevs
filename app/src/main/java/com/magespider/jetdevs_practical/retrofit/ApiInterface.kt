package com.magespider.jetdevs_practical.retrofit

import com.magespider.jetdevs_practical.model.LoginResponseBean
import com.magespider.jetdevs_practical.utils.ApiConstant
import retrofit2.http.*

interface ApiInterface {

    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )
    /**
     * Api Call
     */

    @FormUrlEncoded
    @POST(ApiConstant.LOGIN)
    suspend fun login(  @Field("username") username: String,
                        @Field("password") password: String,): LoginResponseBean


}