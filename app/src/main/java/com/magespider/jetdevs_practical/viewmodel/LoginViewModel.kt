package com.magespider.jetdevs_practical.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.magespider.jetdevs_practical.model.LoginResponseBean
import com.magespider.jetdevs_practical.repository.LoginRepository
import com.magespider.jetdevs_practical.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(private val loginRepository: LoginRepository) : ViewModel(){


    private val _dataSateLogin: MutableLiveData<DataState<LoginResponseBean>> = MutableLiveData()

    val dataStateLogin: LiveData<DataState<LoginResponseBean>>
        get() = _dataSateLogin

    fun setStateEvent(loginStateEvent: LogInStateEvent) {
        viewModelScope.launch {
            when (loginStateEvent) {
                is LogInStateEvent.OnLoginClicked -> {
                    Log.d(
                        "SIGNUP_VIEWMODEL",
                        "SIGNUP_VIEWMODEL -> ${loginStateEvent.username.toString()}"
                    )
                    loginRepository.getLoginApiCall(loginStateEvent.username, loginStateEvent.password)
                        .onEach {
                            _dataSateLogin.value = it
                        }
                        .launchIn(viewModelScope)
                }
                else -> {

                }
            }
        }
    }
}

sealed class LogInStateEvent() {

    data class OnLoginClicked(val username : String, val password : String) : LogInStateEvent()
}