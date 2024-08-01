package com.example.funprimeassesment.di

import com.example.funprimeassesment.BuildConfig
import com.example.funprimeassesment.data_layer.api.ImagesApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetModule {
    private fun getDynamicRetrofitClient(
        baseUrl: String,
    ): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient= OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideTweet(): ImagesApi {
        return getDynamicRetrofitClient(BuildConfig.BASE_URL_IMAGES).create(ImagesApi::class.java)
    }

}