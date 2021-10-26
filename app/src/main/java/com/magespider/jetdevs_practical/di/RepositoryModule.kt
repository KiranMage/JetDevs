package com.magespider.jetdevs_practical.di

import com.magespider.jetdevs_practical.pref.PrefManager
import com.magespider.jetdevs_practical.repository.LoginRepository
import com.magespider.jetdevs_practical.retrofit.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesSignUpRepository(
        apiInterface: ApiInterface, prefManager: PrefManager
    ): LoginRepository {
        return LoginRepository(apiInterface, prefManager)
    }
}
