package com.magespider.jetdevs_practical.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.magespider.jetdevs_practical.pref.PrefConstant
import com.magespider.jetdevs_practical.pref.PrefManager
import com.magespider.jetdevs_practical.retrofit.ApiInterface
import com.magespider.jetdevs_practical.utils.ApiConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    fun provideOkHttPClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        prefManager: PrefManager
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain: Interceptor.Chain ->
                val request: Request =
                    chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                Log.d("TAG", "provideOkHttPClient: $")
                prefManager.setString(PrefConstant.PREF_X_ACC_TOKEN, "eyJhbGciOiJSUzUxMiJ9.eyJsYXN0TG9naW4iOiIxOSBEZWNlbWJlciAyMDE3IDE3OjA4OjM0IiwiZmxhZ0ZpcnN0VG9rZW4iOiJOIiwibGxldiI6IkhtYWNTSEE1MTIiLCJpc3MiOiJCRVJUSE8iLCJmdWxsTmFtZSI6IkJybyBCZXJ0aG8iLCJmbGFnX2NpcyI6InRydWUiLCJGbGFnTWFuZGF0b3J5U01TIjoiWSIsInNkIjoxNTEzNjc4MTE5OTI4LCJzayI6IlpHRXdZekEwWVRVdFpHVTBOaTAwT1RrekxXRXdORFF0T0RsaU9ETXlZakF6TlRjdyIsImZsYWdUb2tlbiI6Ik4iLCJleHAiOjE1MTM2Nzg3MTksImp0aSI6IjU3NWFlNjFiLTFhNWEtNDFiOC05YzM0LTU5NDc5MWIzYzk5YSIsImZ0bCI6ZmFsc2V9.U021m_lwQC8BGcOJggfKRtbEYneVcjdYszGZTfNWbiaegeI9BFwaIZYIGhZgKHlsiRp5YhPjvK3YzbVxdjjubjrzjFdD4Ovd_V2JVc65Jmg755_nr5Qg6YGk9hW3vf404ZXJmYx0N0cTwrjjxY9HGNgi9Tub_krDKUpXCPctOEHNUJ3y86RbFHYYonpyxRNbahFsWkU-X9zoBRdIUaA3yVhf8enfuSTgCHOO__RU7JPkin7atdwWT_yqZ3WGTpO7BZw45e3bF-tkDMbfKcAmyA2dNSnWksOZHKpuTLUfV2vazXruJcjJqvqyJAzFTXhO30WxQY385-DRxYzPoDCqlw")
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): ApiInterface {
        return retrofit
            .client(okHttpClient)
            .build()
            .create(ApiInterface::class.java)
    }
}