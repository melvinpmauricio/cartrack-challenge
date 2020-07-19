package com.cartrack.challenge.di

import androidx.annotation.NonNull
import com.cartrack.challenge.BuildConfig
import com.cartrack.challenge.network.services.UserService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectionPool(ConnectionPool(3, 10, TimeUnit.MINUTES))

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.networkInterceptors().add(logging)
        }

        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitInterface(httpClient: OkHttpClient): Retrofit {
        val gsonBuilder = GsonBuilder()
            .create()

        //add retro builder
        val retroBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))

        retroBuilder.client(httpClient)
        return retroBuilder.build()
    }

    @Provides
    @Singleton
    @NonNull
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}