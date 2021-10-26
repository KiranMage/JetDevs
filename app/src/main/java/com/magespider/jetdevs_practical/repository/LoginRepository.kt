package com.magespider.jetdevs_practical.repository

import android.util.Log
import com.magespider.jetdevs_practical.model.LoginResponseBean
import com.magespider.jetdevs_practical.pref.PrefConstant
import com.magespider.jetdevs_practical.pref.PrefManager
import com.magespider.jetdevs_practical.retrofit.ApiInterface
import com.magespider.jetdevs_practical.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class LoginRepository
constructor(private var apiInterface: ApiInterface, private var prefManager: PrefManager) {

    suspend fun getLoginApiCall(
        username: String,
        password: String
    ): Flow<DataState<LoginResponseBean>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiInterface.login(username, password)
            if(response.errorCode == "00") {
                Log.d("TAG", "getLoginApiCall: $response")
                prefManager.setString(PrefConstant.PREF_USER_ID, response.user!!.userId)
                prefManager.setString(PrefConstant.PREF_USER_NAME, response.user.userName)
                emit(DataState.Success(response))
            }else {
                emit(DataState.CustomException(response.errorMessage!!))
            }
        } catch (e: HttpException) {
            emit(DataState.Error(e))
        }
    }
}