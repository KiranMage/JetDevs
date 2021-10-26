package com.magespider.jetdevs_practical.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.magespider.jetdevs_practical.R
import com.magespider.jetdevs_practical.databinding.ActivityHomeBinding
import com.magespider.jetdevs_practical.pref.PrefConstant
import com.magespider.jetdevs_practical.pref.PrefManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val mTAG = HomeActivity::class.java.simpleName
    lateinit var binding : ActivityHomeBinding

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        binding.txtUserNameValue.text = prefManager.getString(PrefConstant.PREF_USER_NAME)
        binding.txtUserIdValue.text = prefManager.getString(PrefConstant.PREF_USER_ID)
        binding.txtXAccValue.text = prefManager.getString(PrefConstant.PREF_X_ACC_TOKEN)
    }
}