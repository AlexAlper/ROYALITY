package com.evgeniy.restapp.DI.Modules

import com.evgeniy.royality.Net.Api
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule(private val baseUrl: String) {

    @Provides
    @Singleton
    internal fun provideApi(gson: Gson, okHttpClient: OkHttpClient): Api {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
                .create(Api::class.java)
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
//                .registerTypeAdapter()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .disableHtmlEscaping()
                .create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient().newBuilder()
        clientBuilder.addInterceptor(logging)
//        clientBuilder.addInterceptor(TokenInterceptor(apiV))
//        clientBuilder.followRedirects(true)
//        clientBuilder.cache(cache)
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        clientBuilder.readTimeout(0, TimeUnit.SECONDS)
        return clientBuilder.build()
    }
}