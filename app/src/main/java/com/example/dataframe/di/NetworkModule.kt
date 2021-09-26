package com.example.dataframe.di

import com.example.dataframe.api.service.ApiService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun providesRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesOkHttpClient(requestInterceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addNetworkInterceptor(StethoInterceptor())
        return client.build()
    }

    @Provides
    fun providesOkHttpRequestInterceptor(): Interceptor {
        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequestBuilder = originalRequest.newBuilder()
            newRequestBuilder.run {
                addHeader("Accept", "application/json")
                addHeader("Content-Type", "application/json")
                addHeader("Connection", "close")
            }
            val newRequest = newRequestBuilder.build()
            chain.proceed(newRequest)
        }
        return interceptor
    }
}