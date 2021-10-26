package com.magespider.jetdevs_practical.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.magespider.jetdevs_practical.R
import com.magespider.jetdevs_practical.databinding.ActivityLoginBinding
import com.magespider.jetdevs_practical.model.LoginResponseBean
import com.magespider.jetdevs_practical.pref.PrefConstant
import com.magespider.jetdevs_practical.pref.PrefManager
import com.magespider.jetdevs_practical.utils.DataState
import com.magespider.jetdevs_practical.utils.Utils
import com.magespider.jetdevs_practical.viewmodel.LogInStateEvent
import com.magespider.jetdevs_practical.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val mTAG = LoginActivity::class.java.simpleName
    lateinit var binding: ActivityLoginBinding
    private var lastClickTime: Long = 0


    @ExperimentalCoroutinesApi
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager.clearAll()
        loginSubScribeObservers()
        initUi()
    }

    private fun initUi() {
        binding.btnSignIn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnSignIn -> {
                if (SystemClock.elapsedRealtime() - lastClickTime < 500) {
                    return
                }
                lastClickTime = SystemClock.elapsedRealtime()
                Utils.hideKeyboard(this, view)
                if (isValid()) {
                    viewModel.setStateEvent(
                        LogInStateEvent.OnLoginClicked(
                            binding.edtuseranme.text.toString(),
                            binding.edtPassword.text.toString()
                        )
                    )
                }
            }
        }
    }

    private fun isValid(): Boolean {
        binding.inputUsername.isErrorEnabled = false
        binding.inputPassword.isErrorEnabled = false
        when {
            binding.edtuseranme.text.isNullOrEmpty() -> {
                binding.inputUsername.isErrorEnabled = true
                binding.inputUsername.error = getString(R.string.error_user_name)
                binding.inputUsername.requestFocus()
                return false
            }
            binding.edtPassword.text.toString().isNullOrEmpty() -> {
                binding.inputPassword.isErrorEnabled = true
                binding.inputPassword.error = getString(R.string.error_password)
                binding.inputPassword.requestFocus()
                return false
            }

        }
        return true
    }

    private fun loginSubScribeObservers() {
        viewModel.dataStateLogin.observe(this, { dataState ->
            when (dataState) {
                is DataState.Success<LoginResponseBean> -> {
                    displayProgressBar(false)
                    Log.d(mTAG, "loginSubScribeObservers: ${dataState.data.user}")
                    Log.d(mTAG, "loginSubScribeObservers: ${prefManager.getString(PrefConstant.PREF_USER_NAME)}")
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                }
                is DataState.CustomException -> {
                    displayProgressBar(false)
                    displayError(dataState.message)
                    Log.d(mTAG, "ERROR MESSAGE-> ${dataState.message}")
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message())
                    Log.d(mTAG, "ERROR MESSAGE-> ${dataState.exception.message()}")
                    Log.d(mTAG, "ERROR MESSAGE-> ${dataState.exception}")
                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@LoginActivity, "Unknown error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

}