package com.magespider.jetdevs_practical.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class PrefManager @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PrefConstant.PREF_FILE, MODE_PRIVATE)


    fun setString(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun setArray(key: String, value: IntArray) {
        sharedPreferences.edit().putString(
            key, value.joinToString(
                separator = ",",
                transform = { it.toString() })
        ).apply()
    }

    fun getArray(key: String): IntArray {
        with(getString(key)) {
            with(if (this!!.isNotEmpty()) split(',') else return intArrayOf()) {
                return IntArray(count()) { this[it].toInt() }
            }
        }
    }

    fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun setLong(key: String, value: Long?) {
        sharedPreferences.edit().putLong(key, value!!).apply()
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, -1)
    }

    fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun clearExcept(keyList: List<String>) {
        val keys = sharedPreferences.all
        for ((key) in keys) {
            if (!keyList.contains(key)) {
                removeValue(key!!)
            }
        }
    }
}